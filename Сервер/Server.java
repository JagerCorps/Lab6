import Data.Organisation;
import Services.*;

import java.io.*;
import java.net.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Главный класс сервера
 */
public class Server {

    /**
     * Поле времени
     */
    private static LocalDateTime time;

    /**
     * Менеджер сервера. Принимает подключения, организовывает чтение из канала, запись в канал.
     */
    private static ServerManager serverManager;

    /**
     * Канал сервера
     */
    private static ServerSocketChannel channel;

    /**
     * Селектор. Будет мониторить подключающиеся каналы
     */
    private static Selector selector;

    /**
     * Главный метод сервера
     * @param args - аргументы командной строки
     */
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        log("INFO: Начало работы.");
        log("INFO: Попытка получения переменной окружения...");
        String path = System.getenv("Lab5");
        path = "C:\\Users\\mrgle\\Desktop\\Программирование\\Лаба 5\\Jager.xml";
        log("INFO: Переменная окружения получена.");
        log("INFO: Попытка чтения данных из файла...");
        PriorityQueue<Organisation> organisations;
        organisations = Parser.read(path, scanner);
        ServerInterpreter interpreter = new ServerInterpreter(organisations);
        File file = new File(path);
        interpreter.setFile(file);

        log("INFO: Данные из файла успешно загружены.");
        log(" Начало работы.");

        try {
            log("INFO: Сервер запускается...");

            int port = 4004;
            InetAddress hostIP = InetAddress.getLocalHost();
            channel = ServerSocketChannel.open();
            selector = Selector.open();
            InetSocketAddress address = new InetSocketAddress(hostIP,port);
            channel.configureBlocking(false);
            channel.bind(address);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            serverManager = new ServerManager(channel, selector, interpreter);


            log("INFO: Сервер запущен.");

            while(true) {
                try{
                    selector.selectNow();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    checkConsole(organisations, file);

                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                    while(keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();

                        if (!key.isValid()){
                            continue;
                        }

                        if(key.isAcceptable()) {
                            log("INFO: Запрос на подключение клиента...");
                            serverManager.accept(key);
                            log("INFO: Клиент успешно подключен.");
                        } else if(key.isReadable()) {
                            log("INFO: Попытка чтения из канала...");
                            try{
                                serverManager.read(key);
                                key.interestOps(SelectionKey.OP_WRITE);
                                log("INFO: Чтение из канала прошло успешно.");
                            }
                            catch (Exception e){
                                log("WARNING: Клиент отключился.");
                                key.cancel();
                            }

                        } else if(key.isWritable()) {
                            log("INFO: Попытка отправки ответа клиенту...");
                            serverManager.write(key);
                            log("INFO: Сообщение клиенту успешно отправлено.");
                        }
                        keyIterator.remove();
                    }
                }
                catch (Exception ignore){

                }

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Метод для проверки консоли сервера
     * @param organisations - коллекция элементов класса {@link Organisation}
     * @param file - файл, в котором содержатся данные о коллекции
     */
    private static void checkConsole(PriorityQueue<Organisation> organisations, File file){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        long endOfSession = System.currentTimeMillis() + 500;

        try{
            while (System.currentTimeMillis() <= endOfSession){
                if (bufferedReader.ready()){
                    String command = bufferedReader.readLine();
                    if (command.equals("save")){
                        Parser.write(organisations,file);
                        log("INFO: Данные успешно сохранены в файл.");
                    }
                    if(command.equals("exit")){
                        channel.close();
                        selector.close();
                        bufferedReader.close();
                        log(" Завершение работы.");
                        System.exit(0);
                    }
                }
            }
        }
        catch (Exception e){
        }
    }

    private static void log(String message){
        time = LocalDateTime.now();
        System.out.println(time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss ")) + message);
    }

}

import CommunicationData.ClientCommand;
import Commands.CommandType;
import CommunicationData.Message;
import Data.Organisation;
import Services.*;

import java.io.File;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Главный класс клиента
 */
public class Client {

    /**
     * Поле времени
     */
    private static LocalDateTime time;

    /**
     * Интерпретатор команд
     */
    private static ClientInterpreter interpreter;

    /**
     * Менеджер работы клиента
     */
    private static ClientManager clientManager;

    /**
     * Главный метод клиента
     * @param args - аргументы командной строки
     */
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        try {
            log("INFO: Клиент запускается...");

            int port = 4004;
            InetAddress host = InetAddress.getLocalHost();
            log("INFO: Попытка подключения...");
            Socket socket = new Socket(host, port);

            log("INFO: Клиент запущен. Подключение прошло успешно.");

            interpreter = new ClientInterpreter(scanner);
            clientManager = new ClientManager();
            boolean isDone = false;
            do{
                System.out.println(">>>>>>>");
                try {
                    ClientCommand clientCommand = interpreter.readLine(scanner.nextLine(),false);

                    if (clientCommand.getCommand() == CommandType.exit) {
                        isDone = true;
                    }

                    else if (clientCommand.getCommand() == CommandType.execute_script){
                        //Наведём шороху
                        try {
                            ArrayList<ClientCommand> commandArrayList = new ArrayList<>();
                            Scanner localScanner = new Scanner(new File(clientCommand.getStringField()));
                            do {
                                ClientCommand command = interpreter.readLine(localScanner.nextLine(),true);
                                if (command.getCommand() == CommandType.null_command){
                                    throw new NullPointerException();
                                }
                                else{
                                    commandArrayList.add(command);
                                }
                            } while (localScanner.hasNextLine());
                            for (ClientCommand command: commandArrayList){
                                log("INFO: Попытка выслать команду серверу...");
                                socket.getOutputStream().write(clientManager.serialize(command));
                                log("INFO: Команда успешно отправлена. Получение сообщения...");
                                byte[] buffer = new byte[10000];
                                socket.getInputStream().read(buffer);
                                clientManager.showResult(clientManager.deserialize(buffer));
                                //log("INFO: Получено сообщение. Зачитываю: "+command.getCommand());
                            }
                        }
                        catch (NullPointerException e){
                            log("INFO: Скрипт написан неправильно. Исправьте скрипт.");
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    else if (clientCommand.getCommand() != CommandType.null_command){
                        log("INFO: Попытка выслать команду серверу...");
                        socket.getOutputStream().write(clientManager.serialize(clientCommand));
                        log("INFO: Команда успешно отправлена. Получение сообщения...");
                        byte[] buffer = new byte[10000];
                        socket.getInputStream().read(buffer);
                        clientManager.showResult(clientManager.deserialize(buffer));
                    }
                }
                catch(SocketException e){
                    log("ERROR: Проблемы с подключением к серверу.");
                    log("SOLUTION: Перезапустите клиент: воспользуйтесь командой exit");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            } while (!isDone);

        }
        catch (ConnectException e){
            log("ERROR: Нет подключения к серверу.");
            log(" Завершение работы.");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Метод для логирования
     * @param message - сообщение
     */
    private static void log(String message){
        time = LocalDateTime.now();
        System.out.println(time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss ")) + message);
    }

}

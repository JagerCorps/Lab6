package Services;

import CommunicationData.ClientCommand;
import CommunicationData.Message;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Класс для урегулирования работы сервера
 * Осуществляет подключение клиентов, получение от них запросов и отправку ответов
 */
public class ServerManager implements Deserializer, Serializer, MessageReader, MessageWriter, ClientAcceptor{

    /**
     * Сетевой канал сервера
     */
    private ServerSocketChannel channel;

    /**
     * Селектор на сервере
     */
    private Selector selector;

    /**
     * Серверный интерпретатор для исполнения команд
     */
    private ServerInterpreter interpreter;

    /**
     * Конструктор менеджера сервера. Задаёт основу составляющих работы сервера
     * @param channel - сетевой канал сервера
     * @param selector - селектор
     */
    public ServerManager(ServerSocketChannel channel, Selector selector, ServerInterpreter interpreter){
        this.channel = channel;
        this.selector = selector;
        this.interpreter = interpreter;
    }

    /**
     * Блок, отвечающий за принятие подключений
     * @param key - ключ селектора
     * @throws IOException - исключение ввода/вывода
     */
    @Override
    public void accept(SelectionKey key) throws IOException{
        SocketChannel client = channel.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    /**
     * Блок, осуществляющий чтение запросов
     * @param key - ключ селектора
     * @throws IOException - исключение ввода/вывода
     * @throws ClassNotFoundException - исключение ненайденного класса
     */
    @Override
    public void read(SelectionKey key) throws IOException, ClassNotFoundException{
        ByteBuffer byteBuffer = ByteBuffer.allocate(10000);
        SocketChannel client = (SocketChannel) key.channel();
        client.read(byteBuffer);
        ClientCommand clientCommand =  this.deserialize(byteBuffer.array());
        key.attach(interpreter.readMessage(clientCommand));
    }

    /**
     * Блок, осуществляющий отправку ответов на запросы клиентов
     * @param key - ключ селектора
     * @throws Exception - исключение
     */
    @Override
    public void write(SelectionKey key) throws Exception{
        Message message = (Message) key.attachment();
        SocketChannel clientChannel = (SocketChannel) key.channel();
        clientChannel.write(ByteBuffer.wrap(this.serialize(message)));
        key.interestOps(SelectionKey.OP_READ);
    }

}

package Services;

import CommunicationData.ClientCommand;
import CommunicationData.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Интерфейс, отвечающий за десериализацию полученных байт
 */
public interface Deserializer {

    /**
     * Блок, отвечающий за десериализацию байт
     * @param byteCommand - сериализованный запрос от сервера в виде массива байт
     * @return возвращает десериализованный объект-запрос клиента
     * @throws IOException - исключение ввода/вывода
     * @throws ClassNotFoundException - исключение ненайденного класса
     */
    default ClientCommand deserialize(byte[] byteCommand) throws IOException, ClassNotFoundException {
        try (final ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteCommand))){
            return (ClientCommand) in.readObject();
        }
    }
}

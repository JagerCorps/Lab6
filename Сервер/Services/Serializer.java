package Services;

import CommunicationData.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Интерфейс, отвечающий за сериализацию объектов
 */
public interface Serializer {

    /**
     * Блок, отвечающий за сериализацию сообщений клиента/сервера
     * @param message - сообщение в виде объекта
     * @return возвращает сериализованный объект в виде массива байт
     * @throws IOException - исключение ввода/вывода
     */
    default byte[] serialize(Message message) throws IOException{
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        try (final ObjectOutputStream out = new ObjectOutputStream(byteOut)){
            out.writeObject(message);
        }

        return byteOut.toByteArray();
    }
}

package Services;

import CommunicationData.ClientCommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Интерфейс, отвечающий за сериализацию объектов команд
 */
public interface Serializer {

    /**
     * Блок, отвечающий за сериализацию
     * @param clientCommand - клиентская команда
     * @return возвращает сериализованный объект в потока байтов
     * @throws IOException - исключение ввода/вывода
     */
    default byte[] serialize(ClientCommand clientCommand) throws IOException{
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

        try (final ObjectOutputStream out = new ObjectOutputStream(byteOut)){
            out.writeObject(clientCommand);
        }

        return byteOut.toByteArray();
    }
}

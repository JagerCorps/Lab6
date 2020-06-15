package Commands;

import CommunicationData.Message;

/**
 * Интерфейс команды
 */
public interface Command {

    /**
     * Метод, приводящий команду в исполнение и возвращающий сообщение клиенту
     * @return возвращает сообщение клиенту
     */
    Message execute();
}

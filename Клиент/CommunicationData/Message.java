package CommunicationData;

import java.io.Serializable;

/**
 * Абстрактный класс-оболочка для сообщений между клиентом и сервером
 */
public abstract class Message implements Serializable {

    /**
     * Текст сообщения
     */
    protected String messageText;

    /**
     * Конструктор для задания текста сообщения
     */
    public Message(String messageText){
        this.messageText = messageText;
    }

    /**
     * Метод для получения сообщения
     * @return возвращает в текстовом представлении сообщение
     */
    public String getMessageText() {
        return messageText;
    }
}

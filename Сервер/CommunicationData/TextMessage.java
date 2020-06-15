package CommunicationData;

import java.io.Serializable;

/**
 * Класс текстового сообщения. Содержит только текст-уведомление о выполненной операции
 */
public class TextMessage extends Message implements Serializable {

    /**
     * Конструктор для задания текста сообщения
     * @param messageText - текст сообщения
     */
    public TextMessage(String messageText){
        super(messageText);
    }
}

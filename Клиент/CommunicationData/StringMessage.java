package CommunicationData;

import java.io.Serializable;

/**
 * Класс сообщений с массивом строк
 */
public class StringMessage extends Message implements Serializable {

    /**
     * Поле сроки
     */
    private String[] strings;

    /**
     * Конструктор класса строкосодержащего сообщения
     * @param messageText - текст сообщения
     * @param strings - массив строк
     */
    public StringMessage(String messageText, String[] strings){
        super(messageText);
        this.strings = strings;
    }

    /**
     * Метод, возвращающий массив срок команды
     * @return возвращает массив строк
     */
    public String[] getStrings(){
        return strings;
    }
}

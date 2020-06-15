package CommunicationData;


import Data.Organisation;

import java.io.Serializable;
import java.util.PriorityQueue;

/**
 * Класс обЪектосодержащего сообщения
 */
public class ElementsMessage extends Message implements Serializable {

    /**
     * Массив элементов
     */
    private Organisation[] organisations;

    /**
     * Конструктор класса обЪектосодержащего сообщения
     * @param textMessage - текстовое сообщение
     * @param organisationCollection - коллекция объектов класса {@link Organisation}
     */
    public ElementsMessage(String textMessage, Organisation[] organisationCollection){
        super(textMessage);
        this.organisations = organisationCollection;
    }

    /**
     * Метод для получения массива элементов
     * @return возвращает массив элементов
     */
    public Organisation[] getOrganisations() {
        return organisations;
    }
}

package Commands;

import CommunicationData.Message;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Класс команды add_if_max
 */
public class AddIfMax extends Add implements Command {

    /**
     * Поле максимального элемента коллекции
     */
    protected Organisation maxOrganisation;

    /**
     * Конструктор класса команды add_if_max
     * @param organisations - коллекция объектов класса {@link Organisation}
     */
    public AddIfMax(PriorityQueue<Organisation> organisations, Organisation newOrg){
        super(organisations, newOrg);
        this.getMaxOrganisation();
    }

    /**
     * Метод, возвращающий максимальный элемент коллекции
     * @return возвращает максимальный элемент
     */
    protected void getMaxOrganisation() {
        maxOrganisation = organisations.stream()
                .max(Organisation::compareTo)
                .get();
    }
    /**
     * Метод, приводящий команду в исполнение
     * @return возвращает сообщение клиенту
     */
    @Override
    public Message execute(){
        if (this.maxOrganisation != null) {
            newOrg.setId(this.idCounter() + 1);
            if (newOrg.compareTo(this.maxOrganisation) > 0) {
                organisations.add(newOrg);
                return new TextMessage("Элемент успешно добавлен в коллекцию");
            } else {
                return new TextMessage("Добавляемый элемент меньше максимального. Элемент не добавлен");
            }
        } else {
            newOrg.setId(this.idCounter() + 1);
            organisations.add(newOrg);
            return new TextMessage("Элемент успешно добавлен в коллекцию");
        }
    }
}

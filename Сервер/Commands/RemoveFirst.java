package Commands;

import CommunicationData.Message;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.PriorityQueue;

/**
 * Класс команды remove_first
 */
public class RemoveFirst extends AbstractCommand implements Command {

    /**
     * Конструктор класса команды remove_first
     * @param organisations - коллекция объектов класса организации {@link Organisation}
     */
    public RemoveFirst(PriorityQueue<Organisation> organisations){
        super(organisations);
    }

    /**
     * Метод, приводящий команду в исполнение
     * @return возвращает сообщение клиенту
     */
    @Override
    public Message execute(){
        organisations.poll();
        return new TextMessage("Первый элемент коллекции успешно удалён");
    }
}

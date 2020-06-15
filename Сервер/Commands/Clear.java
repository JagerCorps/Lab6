package Commands;

import CommunicationData.Message;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.PriorityQueue;

/**
 * Класс команды clear
 */
public class Clear extends AbstractCommand implements Command{

    /**
     * Конструктор класса команды clear
     * @param organisations - коллекция объектов класса организации {@link Organisation}
     */
    public Clear(PriorityQueue<Organisation> organisations){
        super(organisations);
    }

    /**
     * Метод, приводящий команду в исполнени
     * @return возвращает сообщение клиенту
     */
    @Override
    public Message execute(){
        organisations.clear();
        return new TextMessage("Коллекция успешно очищена");
    }
}

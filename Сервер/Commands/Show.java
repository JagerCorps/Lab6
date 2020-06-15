package Commands;

import CommunicationData.Message;
import CommunicationData.ElementsMessage;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.PriorityQueue;

/**
 * Класс команды show
 */
public class Show extends AbstractCommand implements Command{

    /**
     * Конструктор класса команды show
     * @param organisations - коллекция объектов класса организации {@link Organisation}
     */
    public Show(PriorityQueue<Organisation> organisations){
        super(organisations);
    }

    /**
     * Метод, приводящий команду в исполнение
     * @return возвращает сообщение клиенту
     */
    @Override
    public Message execute(){
        Organisation[] helpArray = new Organisation[organisations.size()];
        if (organisations.size() == 0){
            return new TextMessage( "Коллекция пуста.");
        }
        else{
            return new ElementsMessage("Коллекция содержит элементы:", organisations.toArray(helpArray));
        }
    }

}


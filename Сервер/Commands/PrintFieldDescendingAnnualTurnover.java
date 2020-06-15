package Commands;

import CommunicationData.FloatMessage;
import CommunicationData.Message;
import CommunicationData.ElementsMessage;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Класс команды print_field_descending_annual_turnover
 */
public class PrintFieldDescendingAnnualTurnover extends AbstractCommand implements Command {

    /**
     * Конструктор класса команды print_field_descending_annual_turnover
     * @param organisations - коллекция объектов класса организации {@link Organisation}
     */
    public PrintFieldDescendingAnnualTurnover(PriorityQueue<Organisation> organisations){
        super(organisations);
    }

    /**
     * Метод, приводящий команду в исполнение
     * @return возвращает сообщение клиенту
     */
    @Override
    public Message execute(){
        if (organisations.size() > 0){
            Float[] floats = new Float[organisations.size()];
            ArrayList<Float> turnovers = organisations.stream()
                    .map(Organisation::getAnnualTurnover)
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toCollection(ArrayList::new));
            return new FloatMessage("Значения полей annualTurnover в порядке убывания:", turnovers.toArray(floats));
        }
        else{
            return new TextMessage("Коллекция пуста.");
        }
    }
}

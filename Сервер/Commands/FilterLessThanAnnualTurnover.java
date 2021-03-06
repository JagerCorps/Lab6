package Commands;

import CommunicationData.Message;
import CommunicationData.ElementsMessage;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Класс команды filter_less_than_annual_turnover
 */
public class FilterLessThanAnnualTurnover extends AbstractCommand implements Command{

    /**
     * Поле годового оборота
     */
    protected Float annualTurnover;

    /**
     * Конструктор команды filter_less_than_annual_turnover
     * @param organisations - коллекция элементов класса {@link Organisation}
     * @param annualTurnover - годовой оборот
     */
    public FilterLessThanAnnualTurnover(PriorityQueue<Organisation> organisations, Float annualTurnover){
        super(organisations);
        this.annualTurnover = annualTurnover;
    }

    /**
     * Метод, приводящий команду в исполнение
     * @return возвращает сообщение клиенту
     */
    @Override
    public Message execute(){
        if (organisations.size() > 0){
            PriorityQueue<Organisation> orgQueue = organisations.stream()
                    .filter(organisation -> organisation.getAnnualTurnover() < annualTurnover)
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toCollection(PriorityQueue::new));
            if (orgQueue.size() > 0){
                Organisation[] helpOrg = new Organisation[orgQueue.size()];
                return new ElementsMessage("Элементы по вашему запросу:", orgQueue.toArray(helpOrg));
            }
            else{
                return new TextMessage("Нет элементов, удовлетворяющих запросу.");
            }
        }
        else{
            return new TextMessage("Коллекция пуста");
        }
    }
}

package Commands;

import CommunicationData.Message;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Класс команды remove_lower
 */
public class RemoveLower extends AbstractCommand implements Command {

    /**
     * Количество сотрудников. Требуется для сравнения
     */
    protected int employees;

    /**
     * Конструктор класса команды remove_lower
     * @param organisations - коллекция элементов класса {@link Organisation}
     * @param employees - количество сотрудников
     */
    public RemoveLower(PriorityQueue<Organisation> organisations, int employees){
        super(organisations);
        this.employees = employees;
    }

    /**
     * Метод, приводящий команду в исполнение
     * @return возвращает сообщение для клиента
     */
    @Override
    public Message execute(){
        int size = organisations.size();
        if (size > 0){
            organisations = organisations.stream()
                    .filter(organisation -> organisation.getEmployeesCount() > employees)
                    .collect(Collectors.toCollection(PriorityQueue::new));
            if(size > organisations.size()){
                return new TextMessage("Удаление элементов произошло успешно.");
            }
            else {
                return new TextMessage("Элементы, подлежащие удалению, отсутствуют.");
            }
        }
        else{
            return new TextMessage("Коллекция пуста.");
        }
    }
}

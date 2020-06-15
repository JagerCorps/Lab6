package Commands;

import CommunicationData.Message;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Класс команды remove_by_id
 */
public class RemoveById extends AbstractCommand implements Command {

    /**
     * id элемента
     */
    protected int id;

    /**
     * Конструктор класса команды remove_by_id
     * @param organisations - коллекция элементов класса {@link Organisation}
     * @param id - id элемента
     */
    public RemoveById(PriorityQueue<Organisation> organisations, int id){
        super(organisations);
        this.id = id;
    }

    /**
     * Метод, приводящий команду в исполнение
     * @return возвращает сообщение клиенту
     */
    @Override
    public Message execute() {
        int size = organisations.size();
        if (size > 0){
            organisations = organisations.stream()
                    .filter(o -> o.getId() != id)
                    .collect(Collectors.toCollection(PriorityQueue::new));
            if (size == organisations.size()) {
                return new TextMessage("Элемента с данным id не существует.");
            }
            else {
                return new TextMessage("Элемент по заданному id успешно удалён.");
            }
        }
        else {
            return new TextMessage("Коллекция пуста");
        }
    }
}


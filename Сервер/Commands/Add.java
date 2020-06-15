package Commands;

import CommunicationData.Message;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Класс для реализации команды add
 */
public class Add extends AbstractCommand implements Command{

    /**
     * Поле объекта класса {@link Organisation}, который подлежит додавить в коллекцию
     */
    protected Organisation newOrg;


    /**
     * Конструктор класса команды add
     * @param organisations - коллекция объектов класса {@link Organisation}
     * @param newOrg - добавляемый элемент
     */
    public Add(PriorityQueue<Organisation> organisations, Organisation newOrg){
        super(organisations);
        this.newOrg = newOrg;
    }

    /**
     * Метод, возвращабщий максимальное значение id среди всех элементов коллекции
     * Нужно для того, чтобы каждый новый элемент имел уникальный id
     * При создании элемента, ему задаётся id, который на 1 больше максимального
     * Если коллекция пуста, новый элемент получит id = 1, так как метод вернёт 0
     * @return возвращает максимальный id среди всех элементов коллекции
     */
    protected int idCounter(){
        if (organisations.size()>0){
            return organisations.stream()
                    .map(Organisation::getId)
                    .max(Comparator.naturalOrder())
                    .get();
        }
        else{
            return 0;
        }
    }

    /**
     * Метод, приводящий команду в исполнение
     * @return возвращает сообщение клиенту
     */
    @Override
    public Message execute(){
        newOrg.setId(this.idCounter() + 1);
        organisations.add(newOrg);

        return new TextMessage("Элемент успешно добавлен в коллекцию");
    }
}
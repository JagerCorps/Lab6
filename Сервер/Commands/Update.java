package Commands;

import CommunicationData.Message;
import CommunicationData.TextMessage;
import Data.Organisation;

import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Класс команды update
 */
public class Update extends Add implements Command {

    /**
     * id элемента
     */
    protected int id;
    /**
     * Конструктор класса команды update
     * @param organisations - коллекция элементов класса {@link Organisation}
     * @param id - id элемента
     */
    public Update(PriorityQueue<Organisation> organisations, Organisation newOrg, int id){
        super(organisations, newOrg);
        this.id = id;
    }

    /**
     * Метод, приводящий команду в исполнение
     * @return возвращает сообщение клиенту
     */
    @Override
    public Message execute(){
        int size = organisations.size();
        if (size > 0){
            PriorityQueue<Organisation> updatedOrganisations = organisations.stream()
                    .filter(o -> o.getId() != id)
                    .collect(Collectors.toCollection(PriorityQueue::new));
            if (size > updatedOrganisations.size()){
                organisations = updatedOrganisations;
                newOrg.setId(id);
                organisations.add(newOrg);
                return new TextMessage("Элемент с данным id успешно обновлён");
            }
            else{
                return new TextMessage("Элемента с таким id не существует");
            }
        }
        else{
            return new TextMessage("Коллекция пуста.");
        }
    }
}

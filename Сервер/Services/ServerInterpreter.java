package Services;

import Commands.*;
import CommunicationData.ClientCommand;
import CommunicationData.Message;
import Data.Organisation;

import java.io.File;
import java.util.PriorityQueue;

public class ServerInterpreter {


    /**
     * Коллекция элементов класса {@link Organisation}
     */
    protected PriorityQueue<Organisation> organisations;

    /**
     * Файл, в который будет происходить сохранение данных
     */
    protected File file;

    /**
     * Конструктор интерпретатора
     * @param organisations - коллекция элементов класса {@link Organisation}
     */
    public ServerInterpreter(PriorityQueue<Organisation> organisations){
        this.organisations = organisations;
    }

    /**
     * Метод, который задаёт интерпретатору файл с данными
     * @param file - файл
     */
    public void setFile(File file){
        this.file = file;
    }

    /**
     * Метод, считывающий строку
     * @param inputCommand - строка с командой
     */
    public Message readMessage(ClientCommand inputCommand){
        CommandType type = inputCommand.getCommand();
        switch (type){
            case help:
                Help help = new Help();
                return help.execute();

            case info:
                Info info = new Info(organisations);
                return info.execute();

            case show:
                Show show = new Show(organisations);
                return show.execute();

            case add:
                Add add = new Add(organisations,inputCommand.getElement());
                return add.execute();

            case update:
                Update update = new Update(organisations, inputCommand.getElement(), inputCommand.getIntegerValue());
                return update.execute();

            case remove_by_id:
                RemoveById removeById = new RemoveById(organisations, inputCommand.getIntegerValue());
                return removeById.execute();

            case clear:
                Clear clear = new Clear(organisations);
                return clear.execute();

            case remove_first:
                RemoveFirst removeFirst = new RemoveFirst(organisations);
                return removeFirst.execute();

            case add_if_max:
                AddIfMax addIfMax = new AddIfMax(organisations,inputCommand.getElement());
                return addIfMax.execute();

            case remove_lower:
                RemoveLower removeLower = new RemoveLower(organisations, inputCommand.getIntegerValue());
                return removeLower.execute();

            case remove_all_by_full_name:
                RemoveAllByFullName removeAllByFullName = new RemoveAllByFullName(organisations,inputCommand.getStringField());
                return removeAllByFullName.execute();

            case filter_less_than_annual_turnover:
                FilterLessThanAnnualTurnover fLTAT = new FilterLessThanAnnualTurnover(organisations, inputCommand.getTurnover());
                return fLTAT.execute();

            case print_field_descending_annual_turnover:
                PrintFieldDescendingAnnualTurnover pFDAT = new PrintFieldDescendingAnnualTurnover(organisations);
                return pFDAT.execute();

            default:
                return null;
        }
    }
}

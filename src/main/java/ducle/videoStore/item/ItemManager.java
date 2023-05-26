/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 202301
  Assessment: Project
  Author: Le Minh Duc
  ID: s4000577
  Created  date: 29/05/2023
  Acknowledgement: I have acknowledged that all the resources here are the course materials as well as my own experiences
  Purpose: This class has the utilities functions to handle all the items data gained from the database (.txt files)
*/

package ducle.videoStore.item;

import java.util.*;

public class ItemManager {
    private Map<String, Record> recordMap;
    private Map<String, DVD> dvdMap;
    private Map<String, Game> gameMap;

    public ItemManager(){
        recordMap = new HashMap<>();
        dvdMap = new HashMap<>();
        gameMap = new HashMap<>();
    }
    
    public Map<String, Record> getRecordMap() {
        return recordMap;
    }

    public Map<String, DVD> getDvdMap() {
        return dvdMap;
    }

    public Map<String, Game> getGameMap() {
        return gameMap;
    }

    public List<Record> getRecordList(){
        List<Record> result = new ArrayList<>(recordMap.values());
        Collections.sort(result);
        return result;
    }

    public List<DVD> getDvdList(){
        List<DVD> result = new ArrayList<>(dvdMap.values());
        Collections.sort(result);
        return result;
    }

    public List<Game> getGameList(){
        List<Game> result = new ArrayList<>(gameMap.values());
        Collections.sort(result);
        return result;
    }

    public List<Item> getItemList(){
        List<Item> result = new ArrayList<>();

        result.addAll(this.getRecordList());
        result.addAll(this.getDvdList());
        result.addAll(this.getGameList());

        Collections.sort(result);
        return result;
    }

    public List<Item> getOOSItemList(){
        List<Item> result = new ArrayList<>();

        for(Record item : recordMap.values()){
            if(item.getStock() == 0){
                result.add(item);
            }
        }

        for(DVD item : dvdMap.values()){
            if(item.getStock() == 0){
                result.add(item);
            }
        }

        for(Game item : gameMap.values()){
            if(item.getStock() == 0){
                result.add(item);
            }
        }

        Collections.sort(result);
        return result;
    }

    public List<Item> getISItemList(){
        List<Item> result = new ArrayList<>();

        for(Record item : recordMap.values()){
            if(item.getStock() > 0){
                result.add(item);
            }
        }

        for(DVD item : dvdMap.values()){
            if(item.getStock() > 0){
                result.add(item);
            }
        }

        for(Game item : gameMap.values()){
            if(item.getStock() > 0){
                result.add(item);
            }
        }

        Collections.sort(result);
        return result;
    }

    public String addRecord(Record item){
        recordMap.put(item.getId(), item);
        return "Added Record " + item.getId();
    }

    public String addDvd(DVD item){
        dvdMap.put(item.getId(), item);
        return "Added DVD " + item.getId();
    }

    public String addGame(Game item){
        gameMap.put(item.getId(), item);
        return "Added Game " + item.getId();
    }

    public String addItem(Item item){
        String result = "";

        switch (item.getRentalType()){
            case "Record":
                result = addRecord(new Record(item.getId(), item.getTitle(), item.getLoanType(), item.getStock(), item.getFee(), item.getGenre()));
                break;
            case "DVD":
                result = addDvd(new DVD(item.getId(), item.getTitle(), item.getLoanType(), item.getStock(), item.getFee(), item.getGenre()));
                break;
            case "Game":
                result = addGame(new Game(item.getId(), item.getTitle(), item.getLoanType(), item.getStock(), item.getFee()));
                break;
        }

        return result;
    }

    public Item searchItem(String id){
        Item result = recordMap.get(id);

        if(result == null){
            result = dvdMap.get(id);
        }

        if(result == null){
            result = gameMap.get(id);
        }

        return result;
    }

    public String removeRecord(String id){
        String result;
        Record item = recordMap.remove(id);

        if(item != null){
            result = "Removed record " + item.print();
        }
        else{
            result = "Could not find any record with id " + id;
        }

        return result;
    }

    public String removeDvd(String id){
        String result;
        DVD item = dvdMap.remove(id);

        if(item != null){
            result = "Removed DVD " + item.print();
        }
        else{
            result = "Could not find any DVD with id " + id;
        }

        return result;
    }

    public String removeGame(String id){
        String result;
        Game item = gameMap.remove(id);

        if(item != null){
            result = "Removed Game " + item.print();
        }
        else{
            result = "Could not find any Game with id " + id;
        }

        return result;
    }

    public String removeItem(Item item){
        String result = "";

        if(item instanceof Record){
            result = removeRecord(item.getId());
        }
        else if(item instanceof DVD){
            result = removeDvd(item.getId());
        }
        else if(item instanceof Game){
            result = removeGame(item.getId());
        }

        return result;
    }

    public String removeItem(String id){
        String result;
        Item item = searchItem(id);

        if (item != null){
            result = removeItem(item);
        }
        else{
            result = "Could not find any item with id " + id;
        }

        return  result;
    }

    public String toString(){
        String result = "";

        List<Item> items = getItemList();
        for(Item item : items){
            result +=  item.toString() + "\n";
        }

        return result;
    }
}
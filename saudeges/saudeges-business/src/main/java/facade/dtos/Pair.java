package facade.dtos;

public class Pair<K, V>{

    private K first;
    private V second;

    public static <K,V> Pair<K, V> with(K first, V second){
    	return new Pair<K,V>(first, second);
    }
    
    public Pair(K first, V second){
        this.first = first;
        this.second = second;
    }

    public K getValue0(){
        return first;
    }

    public V getValue1(){
        return second;
    }

}
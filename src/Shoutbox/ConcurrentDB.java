package Shoutbox;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;
public class ConcurrentDB<K,V> {
	TreeMap<K,V> core = new TreeMap<K,V>();
	ReentrantLock  lock = new ReentrantLock ();
    
    
    public ConcurrentDB()
    {
        core = new TreeMap<K,V>();
    }
    public void put(K key,V value)
    {
       lock.lock();
       try
       {
         core.put(key,value);
          }
       finally
       {
           lock.unlock();
       }
    }
    public Entry pop()
    {
        lock.lock();
        try
        {
           Date key =  (Date) core.firstKey(); 
           String value = (String) core.get(key);
           System.out.println(key+":"+value);
           Entry retVal = new Entry(key,value);
           core.remove(key);
           return retVal;
        }
        finally
        {
           lock.unlock();
        }
                
    }
    public  int size(){
        lock.lock();
        try
        {
    	return core.size();}
        
        finally{
            lock.unlock();
        }
    }
}
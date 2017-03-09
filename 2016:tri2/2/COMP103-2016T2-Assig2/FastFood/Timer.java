/**
 * deathray(true); 29/04/2016
 *
 * Timer object
 *
 * returns all the things
 *
 * diffTime returns long of time remaining
 * convertTime returns string of time in proper format
 *
 */
public class Timer
{
    private long time;
    private int distance;
    /**
     * Constructor for objects of class Timer
     */
    public Timer()
    {
        this.time = this.currentTime();
    }

    private long currentTime(){
        return System.currentTimeMillis();
    }

    public void setTime(int time){
        this.time = this.currentTime() + time;
    }

   

    public long diffTime(){
        return this.time - this.currentTime();
    }

    public void addTime(int time){
        this.time+=time;
    }

    public void subTime(int time){
        this.time-=time;
    }

    public String convertTime(long time){
        long milli = (time / 10) % 100;
        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        return String.format("%02d:%02d.%02d", minute, second, milli);
    }

    
}

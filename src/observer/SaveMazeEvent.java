// v2
package observer;

// TODO
public class SaveMazeEvent extends MazeEvent {

    public SaveMazeEvent(MazeEventManager MEM) {
        super(MEM, Type.SAVE_MAZE);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void setStatus(String newStatus) {
        super.setStatus(Statuses, newStatus);
    }
    
}
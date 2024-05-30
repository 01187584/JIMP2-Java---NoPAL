// v2
package observer;

import java.util.HashSet;

import maze.Field;

public class SetFieldTypeEvent extends MazeEvent {
    public static final EventStatusSet Statuses = new EventStatusSet(MazeEvent.Statuses);
    static {
        Statuses.put("START", "Rozpoczęto ustawianie Pola...");
        Statuses.put("PROCESSING", "Ustawianie Pola...");
        Statuses.put("OK", null);

        Statuses.put("SET_FIELD_TYPE_INVALID_COORDS", "Labirynt nie zawiera Pola o podanych współrzędnych.", true);
        Statuses.put("SET_FIELD_TYPE_INVALID_FIELD_TYPE", "Podano nieprawidłowy typ Pola.", true);
    }
    private final int column, row;
    private final char fieldType;
    public static final HashSet<Character> validfieldTypes = new HashSet<>(); // TODO - przenieść to do Field
    static {
        validfieldTypes.add(' ');
        validfieldTypes.add('X');
        validfieldTypes.add('P');
        validfieldTypes.add('K');
    }
    
    public SetFieldTypeEvent(MazeEventManager MEM, int column, int row, char fieldType) {
        super(MEM, Type.SET_FIELD_TYPE);
        //if (!validfieldTypes.contains(fileType)) // Sprawdzane przy performAction()
        this.column = column;
        this.row = row;
        this.fieldType = fieldType;
    }

    @Override
    public void setStatus(String newStatus) {
        super.setStatus(Statuses, newStatus);
    }

    public int getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
    public char getFieldType() {
        return fieldType;
    }

    @Override
    protected void performAction() {
        super.performAction();
        if (!MEM.getMaze().checkBounds(column, row)) {
            setStatus("SET_FIELD_TYPE_INVALID_COORDS");
            return;
        }
        switch (fieldType) {
            case ' ':
                MEM.getMaze().getField(column, row).setFieldType(Field.WHITE_FIELD);
                break;
            case 'X':
                MEM.getMaze().getField(column, row).setFieldType(Field.BLACK_FIELD);
                break;
            case 'P':
                MEM.getMaze().getField(column, row).setFieldType(Field.ENTRANCE_FIELD);
                break;
            case 'K':
                MEM.getMaze().getField(column, row).setFieldType(Field.EXIT_FIELD);
                break;
            default:
                setStatus("SET_FIELD_TYPE_INVALID_FIELD_TYPE");
                return;
        }
        if (fieldType != ' ') setStatus("OK", "Ustawiono Pole ("+String.valueOf(column)+", "+String.valueOf(row)+") na "+fieldType);
        else setStatus("OK", "Ustawiono Pole ("+String.valueOf(column)+", "+String.valueOf(row)+") na przejście");
    }
}
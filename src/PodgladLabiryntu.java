import javax.swing.*;
import java.awt.*;
//import java.util.concurrent.TimeUnit;
import java.awt.event.*;

import maze.Field;
//import maze.Maze;
import observer.MazeEvent;
import observer.MazeEventManager;

public class PodgladLabiryntu extends JPanel {
    //private Random rand;
    private int start_x, start_y;
    private int centering_start_x, centering_start_y;
    //public Maze M;
    private MazeEventManager MEM;
    //private int cols, rows;
    private int sizeofone;
    private int maxwidth, maxheight;
    private Graphics G;
    private int[] tempintarr = new int[2];
    //public boolean selectingEntrance = true; Niepotrzebne, rozróżniamy po kliknięciu LPM lub PPM

    //private int[][] _tempmazetest; // _tempmazetest[kolumna][wiersz] = rodzajPola

    // TODO - zmienić na static shouldFixCoords, shouldFixFlatLaf i enableCentering
    // TODO - zmienić na private shouldFixCoords i shouldFixFlatLaf
    public final boolean shouldFixCoords = false; // Czy automatycznie poprawia podane wymiary (maxwidth, maxheight)
    // oraz współrzędne kliknięte myszką o (-22, -56), aby dobrze się wyświetlało i klikało
    public final boolean shouldFixFlatLaf = false; // Jak powyżej dla FlatLafa, niezależne od powyższego    
    private final boolean enableCentering = true;
    public static final int minSize = 5;
    //private BufferedImage bImage;
    //private JLabel imageLabel;
    //private boolean hasCanvasChanged = true;
    //private List<Point> points = new ArrayList<>();
    
    //public PodgladLabiryntu(int start_x, int start_y, int maxwidth, int maxheight, int cols, int rows) {
    public PodgladLabiryntu(int start_x, int start_y, int maxwidth, int maxheight, MazeEventManager MEM) {
        // Maze M powinien być wcześniej zainicjowany
        //this.M = M;
        this.MEM = MEM;
        //this.rand = new Random();
        //this.cols = cols;this.rows = rows;
        setMaxDimensions(maxwidth, maxheight);
        setStartCoords(start_x, start_y);
        //_tempFillRandomMaze();

        //setBackground(new Color(200,200,200)); // Aby odróżnić od białego
        setBackground(new Color(110,111,113)); // Aby odróżnić od białego
        //setBackground(new Color(23,99,8));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //points.add(new Point(e.getX(), e.getY()));
                //System.out.println(M.toString()); // To generuje dużo spamu i alokuje dużo pamięci na nowe Stringi
                if (CoordsToNums(e.getX(),e.getY()) == null) System.out.println("Współrzędne Pola są nieprawidłowe (nie kliknięto na Pole), pomijam.");
                else {
                    int column, row;
                    column = tempintarr[0];row = tempintarr[1];
                    System.out.printf("CoordsToNums: (%d, %d)\n", column, row);
                    //if (selectingEntrance) {
                    /*if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                        if (M.getField(column, row).isEntranceField()) _tempSetRandomField(column, row);
                        else M.getField(column, row).setFieldType(Field.ENTRANCE_FIELD); // ustawiamy na zielony
                    } else {
                        if (M.getField(column, row).isExitField()) _tempSetRandomField(column, row);
                        else M.setFieldType(M.getField(column, row),Field.EXIT_FIELD); // ustawiamy na czerwony
                    }*/
                    int[] intData = new int[] {column, row, 0};
                    MazeEvent ME = new MazeEvent(MazeEvent.Type.SET_FIELD_TYPE, intData);
                    if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
                        if (MEM.getMaze().getField(column, row).isEntranceField()) intData[2] = Field.getRandomType();
                        else intData[2] = Field.ENTRANCE_FIELD; // ustawiamy na zielony
                    } else {
                        if (MEM.getMaze().getField(column, row).isExitField()) intData[2] = Field.getRandomType();
                        else intData[2] = Field.EXIT_FIELD; // ustawiamy na czerwony
                    }
                    MEM.notifyListeners(ME);
                    
                    //NumsToCoords(column, row);
                    //System.out.printf("NumsToCoords: (%d, %d)\n", column, row);
                    //repaint(); // repaint jest teraz w Oknie po uzyskaniu odpowiedniego MazeEvent
                }
            }
        });
    }
    private void updateCentering() {
        if (enableCentering) {
            if (maxwidth / MEM.getMaze().getCols() < minSize) centering_start_x = start_x;
            else centering_start_x = start_x+((this.maxwidth-(sizeofone*MEM.getMaze().getCols()))/2);
            if (maxheight / MEM.getMaze().getRows() < minSize) centering_start_y = start_y;
            else centering_start_y = start_y+((this.maxheight-(sizeofone*MEM.getMaze().getRows()))/2);
        } else {
            centering_start_x = start_x;
            centering_start_y = start_y;
        }
    }
    public void setStartCoords(int start_x, int start_y) {
        //hasCanvasChanged = true;
        //System.out.println("TRUE 4");
        this.start_x = start_x;this.start_y = start_y;
        // Trzeba zawsze aktualizować (centering_start_x, centering_start_y) przy zmianie wspołrzędnych początkowych lub wymiarów
        updateCentering();
        //System.out.printf("csx %d csy %d sx %d sy %d mw %d mh %d soo %d cols %d rows %d\n",centering_start_x,centering_start_y,start_x,start_y,maxwidth,maxheight,sizeofone,cols,rows);
    }
    public void setMaxDimensions(int maxwidth, int maxheight) {
        //hasCanvasChanged = true;
        //System.out.println("TRUE 5");
        if (shouldFixFlatLaf) {
            fixFlatLaf(maxwidth, maxheight);
            maxwidth = tempintarr[0];maxheight = tempintarr[1];
        }
        //setPreferredSize(new Dimension(maxwidth, maxheight));
        //bImage = new BufferedImage(maxwidth, maxheight, BufferedImage.TYPE_INT_RGB);
        if (shouldFixCoords) {
            fixCoords(maxwidth, maxheight);
            maxwidth = tempintarr[0];maxheight = tempintarr[1];
        }
        this.maxwidth = maxwidth;this.maxheight = maxheight;
        this.sizeofone = Integer.min(maxwidth / MEM.getMaze().getCols(), maxheight / MEM.getMaze().getRows());
        if (this.sizeofone < minSize) this.sizeofone = minSize;
        // Trzeba zawsze aktualizować (centering_start_x, centering_start_y) przy zmianie wspołrzędnych początkowych lub wymiarów
        updateCentering();
        setPreferredSize(new Dimension(centering_start_x+sizeofone*MEM.getMaze().getCols(), centering_start_y+sizeofone*MEM.getMaze().getRows()));
        //setPreferredSize(new Dimension(centering_start_x+sizeofone*M.getCols()+11, centering_start_y+sizeofone*M.getRows()+11)); // ?! Czy to przez ten ScrollPAIN?
    }
    @Override
    public void paintComponent(Graphics g) {
        /*if (hasCanvasChanged == false) {
            System.out.printf("Repainting not required.\n");
            return;
        }*/
        this.G = g;
        System.out.printf("Painting (%d, %d)\n",maxwidth,maxheight);
        super.paintComponent(g);
        /*Graphics g2 = (Graphics2D) g;
        g2.setColor(Color.gray);
        for (Point p : points){
            g2.fillOval(p.x-500, p.y-500, 1000, 1000);
        }*/
        //int size = 20;
        /*
        // Losowy (zdegenerowany) labirynt
        for (int y = centering_start_y; y < centering_start_y+sizeofone*rows;y += sizeofone) {
            for (int x = centering_start_x; x < centering_start_x+sizeofone*cols;x += sizeofone) {
                drawRandom(x, y);
            }
        }
        */
        // Losowy (zdegenerowany) labirynt z możliwością kliknięcia, aby zmienić kolor Pola na czerwony (w celu testowania)
        int x, y;
        for (int r = 1;r <= MEM.getMaze().getRows();r++) {
            for (int c = 1;c <= MEM.getMaze().getCols();c++) {
                NumsToCoords(c, r);
                x = tempintarr[0];y = tempintarr[1];
                switch (MEM.getMaze().getField(c, r).getType()) {
                    case Field.WHITE_FIELD:
                        drawWhite(x, y);
                        break;
                    case Field.BLACK_FIELD:
                        drawBlack(x, y);
                        break;
                    case Field.EXIT_FIELD:
                        drawRed(x, y);
                        break;
                    case Field.ENTRANCE_FIELD:
                        drawGreen(x, y);
                        break;
                    default:
                        System.out.println("Coś jest bez sensu...");
                }
            }
        }
        //hasCanvasChanged = false;
    }
    /*private int randrange(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
    public void drawRandom(int x, int y) {
        if (randrange(0, 1) > 0) drawWhite(x, y);
        else drawBlack(x, y);
    }*/
    // TODO - przenieść drawWhite i drawBlack do klasy Field jako draw() - dzięki temu Pole stanie się odpowiedzialne za sposób rysowania go
    public void drawWhite(int x, int y) {
        //hasCanvasChanged = true;
        //System.out.println("TRUE 1");
        G.setColor(Color.white);
        G.fillRect(x, y, sizeofone, sizeofone);
    }
    public void drawBlack(int x, int y) {
        //hasCanvasChanged = true;
        //System.out.println("TRUE 2");
        G.setColor(Color.black);
        G.fillRect(x, y, sizeofone, sizeofone);
    }
    public void drawRed(int x, int y) {
        //hasCanvasChanged = true;
        //System.out.println("TRUE 3");
        G.setColor(Color.red);
        G.fillRect(x, y, sizeofone, sizeofone);
    }
    public void drawGreen(int x, int y) {
        //hasCanvasChanged = true;
        //System.out.println("TRUE 123");
        G.setColor(Color.green);
        G.fillRect(x, y, sizeofone, sizeofone);
    }
    private int[] fixCoords(int x, int y) {
        tempintarr[0] = x;tempintarr[1] = y;
        if (shouldFixCoords) {
            //tempintarr[0] -= 22;tempintarr[1] -= 56;
            tempintarr[0] -= 202;tempintarr[1] -= 56;
        }
        return tempintarr;
    }
    private int[] fixFlatLaf(int x, int y) {
        tempintarr[0] = x;tempintarr[1] = y;
        if (shouldFixFlatLaf) {
            tempintarr[0] -= 22;tempintarr[1] -= 7;
            //tempintarr[0] -= 220;tempintarr[1] -= 7;
        }
        return tempintarr;
    }

    public int[] CoordsToNums(int x, int y) {
        // Zwraca parę (kolumna, wiersz) odpowiadającą współrzędnym Pola, do którego należy punkt (x,y)
        // Współrzędne Pola są liczone od 1
        // Zwraca null, jeśli współrzędne (x,y) są z poza sensownego zakresu
        System.out.printf("initcoords: (%d, %d)\n", x, y);
        if (sizeofone == 0) {
            tempintarr[0] = 0;tempintarr[1] = 0;return tempintarr; // Aby uniknąć dzielenia przez 0
        }
        tempintarr[0] = x-centering_start_x;tempintarr[1] = y-centering_start_y;
        if (tempintarr[0] <= 0 || tempintarr[1] <= 0) return null;
        System.out.printf("fixedcoords: (%d, %d)\n", tempintarr[0], tempintarr[0]);
        tempintarr[0] /= sizeofone; tempintarr[1] /= sizeofone;
        tempintarr[0]++;tempintarr[1]++; // Pierwsze Pole ma współrzędne (1,1)
        if (tempintarr[0] > MEM.getMaze().getCols() || tempintarr[1] > MEM.getMaze().getRows() || tempintarr[0] <= 0 || tempintarr[1] <= 0) return null;
        return tempintarr;
        //return {};
        //return x/sizeofone;
    }
    public int[] NumsToCoords(int column, int row) {
        // Zwraca parę (x, y) odpowiadającą współrzędnym punktu, w którym zaczyna się Pole o współrzędnych (column, row)
        column--;row--; // Początkowe Pole ma współrzędne (1,1)
        tempintarr[0] = (column)*sizeofone; tempintarr[1] = (row)*sizeofone;
        tempintarr[0] += centering_start_x;tempintarr[1] += centering_start_y;
        return tempintarr;
    }

    public static void main(String [] args) {
        // TODO - zaimportować Maze tylko dla tej nieużywanej metody
        /*JFrame frame = new JFrame();
        //frame.add(new CustomCanvas(0,0,3840,2160,1920,1080));
        frame.setSize(1920,1080);
        int start_x, start_y, maxwidth, maxheight;
        start_x = 0;start_y = 0;
        maxwidth = frame.getBounds().width-start_x;maxheight = frame.getBounds().height-start_y;
        //CustomCanvas C = new CustomCanvas(0,0,frame.getBounds().width,frame.getBounds().height,15,20);
        //PodgladLabiryntu C = new PodgladLabiryntu(start_x,start_y,maxwidth,maxheight,100,100);
        Maze M = new Maze(100, 100);
        PodgladLabiryntu C = new PodgladLabiryntu(start_x,start_y,maxwidth,maxheight,M);
        //frame.add(C);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                C.setMaxDimensions(frame.getBounds().width,frame.getBounds().height);
            }
        });
        JScrollPane Scroll = new JScrollPane(C);
        frame.add(Scroll);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }*/
    }
}
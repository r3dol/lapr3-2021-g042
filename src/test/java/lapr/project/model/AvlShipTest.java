package lapr.project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AvlShipTest {

    List<ShipLocation> arr = new ArrayList<>();

    String[] auxDatas = {"31-12-2020 01:25","31-12-2020 16:15","31-12-2020 17:02"};

    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    ShipLocationBST<ShipLocation> tree;

    BstShip<Ship> ships;

    Ship ship;

    ShipLocation location1;
    ShipLocation location2;
    ShipLocation location3;
    ShipLocation location4;
    ShipLocation location5;
    ShipLocation location6;

    public AvlShipTest() throws ParseException {
        location1 = new ShipLocation("211331640", dateFormatter.parse(auxDatas[0]),"36","-122",19,145,"147","B");
        location2 = new ShipLocation("211331640", dateFormatter.parse(auxDatas[1]),"36","-122",19,145,"147","B");
        location3 = new ShipLocation("211331640", dateFormatter.parse(auxDatas[2]),"36","-122",19,145,"147","B");
        location4 = new ShipLocation("211331640", dateFormatter.parse(auxDatas[0]),"35","-122",19,145,"147","B");
        location5 = new ShipLocation("211331640", dateFormatter.parse(auxDatas[1]),"37","-122",19,145,"147","B");
        location6 = new ShipLocation("211331640", dateFormatter.parse(auxDatas[2]),"38","-122",19,145,"147","B");
        arr.add(location1);
        arr.add(location2);
        arr.add(location3);
    }

    @BeforeEach
    public void setUp(){
        tree = new ShipLocationAVL();
        ships = new AvlShip();
        for(ShipLocation i :arr)
            tree.insert(i);
        ship = new Ship("211331640",",SEOUL EXPRESS","IMO2113432",1,280,"DHBN",70,294,32,"79",13,tree);
        ships.insert(ship);
    }


    @Test
    void insertNull() {
        //ships.insert(null);
    }

    @Test
    void remove() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testEquals1() {
    }
}
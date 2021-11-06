package lapr.project.model;

import lapr.project.utils.BSTInterface;

import java.util.*;

/**
 * Class that represents a ShipLocationBST
 *
 * @author 1201239 Francisco Redol <1201239@isep.ipp.pt>
 * @author Rita Ariana Sobral <1201386@isep.ipp.pt>
 */

public class ShipLocationBST<E> implements BSTInterface<ShipLocation> {

    /** Nested static class for a binary search tree node. */

    protected static class Node<ShipLocation> {
        private ShipLocation shipLocation;          // a ShipLocation stored at this node
        private Node<ShipLocation> left;       // a reference to the left child (if any)
        private Node<ShipLocation> right;      // a reference to the right child (if any)

        /**
         * Constructs a node with the given element and neighbors.
         *
         * @param shipLocation  the element to be stored
         * @param leftChild   reference to a left child node
         * @param rightChild  reference to a right child node
         */
        public Node(ShipLocation shipLocation, Node<ShipLocation> leftChild, Node<ShipLocation> rightChild) {
            this.shipLocation = shipLocation;
            left = leftChild;
            right = rightChild;
        }

        // accessor methods
        public ShipLocation getShipLocation() { return shipLocation; }
        public Node<ShipLocation> getLeft() { return left; }
        public Node<ShipLocation> getRight() { return right; }

        // update methods
        public void setElement(ShipLocation shipLocation) { this.shipLocation = shipLocation; }
        public void setLeft(Node<ShipLocation> leftChild) { left = leftChild; }
        public void setRight(Node<ShipLocation> rightChild) { right = rightChild; }
    }

    //----------- end of nested Node class -----------

    protected Node<ShipLocation> root;     // root of the tree


    /* Constructs an empty binary search tree. */
    public ShipLocationBST() {
        root = null;
    }

    /*
     * @return root Node of the tree (or null if tree is empty)
     */
    protected Node<ShipLocation> root() {
        return root;
    }

    /*
     * Verifies if the tree is empty
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty(){
        return root==null;
    }

    /**
     * Returns the Node containing a specific Element, or null otherwise.
     *
     * @param shipLocation    the element to find
     * @return the Node that contains the Element, or null otherwise
     *
     * This method despite not being essential is very useful.
     * It is written here in order to be used by this class and its
     * subclasses avoiding recoding.
     * So its access level is protected
     */
    protected Node<ShipLocation> find(Node<ShipLocation> node, ShipLocation shipLocation){
        if (node == null)
            return null;
        if (node.getShipLocation().compareTo(shipLocation)==0)
            return node;
        if (node.getShipLocation().compareTo(shipLocation) > 0)
            return find(node.getLeft(),shipLocation);
        else
            return find(node.getRight(),shipLocation);
    }

    /*
     * Inserts an element in the tree.
     */
    public void insert(ShipLocation shipLocation){
        root = insert(shipLocation, root);
    }

    private Node<ShipLocation> insert(ShipLocation shipLocation, Node<ShipLocation> node){
        if(node == null)
            return new Node(shipLocation, null, null);

        if(node.getShipLocation().compareTo(shipLocation) > 0)
            node.setLeft(insert(shipLocation, node.getLeft()));

        else
        if(node.getShipLocation().compareTo(shipLocation) < 0)
            node.setRight(insert(shipLocation, node.getRight()));

        return node;
    }

    /**
     * Removes an element from the tree maintaining its consistency as a Binary Search Tree.
     */
    public void remove(ShipLocation shipLocation){
        root = remove(shipLocation, root());
    }

    private Node<ShipLocation> remove(ShipLocation shipLocation, Node<ShipLocation> node) {

        if (node == null) {
            return null;    //throw new IllegalArgumentException("Element does not exist");
        }
        if (shipLocation.compareTo(node.getShipLocation())==0) {
            // node is the Node to be removed
            if (node.getLeft() == null && node.getRight() == null) { //node is a leaf (has no childs)
                return null;
            }
            if (node.getLeft() == null) {   //has only right child
                return node.getRight();
            }
            if (node.getRight() == null) {  //has only left child
                return node.getLeft();
            }
            ShipLocation min = smallestElement(node.getRight());
            node.setElement(min);
            node.setRight(remove(min, node.getRight()));
        }
        else if (shipLocation.compareTo(node.getShipLocation()) < 0)
            node.setLeft( remove(shipLocation, node.getLeft()) );
        else
            node.setRight( remove(shipLocation, node.getRight()) );

        return node;
    }

    /*
     * Returns the number of nodes in the tree.
     * @return number of nodes in the tree
     */
    public int size(){
        return size(root);
    }

    private int size(Node<ShipLocation> node){
        if(node == null)
            return 0;

        return 1 + size(node.getLeft()) + size(node.getRight());
    }

    /*
     * Returns the height of the tree
     * @return height
     */
    public int height(){
        return height(root);
    }

    /*
     * Returns the height of the subtree rooted at Node node.
     * @param node A valid Node within the tree
     * @return height
     */
    protected int height(Node<ShipLocation> node){
        if (node == null)
            return -1;

        int lDepth = height(node.left);
        int rDepth = height(node.right);

        if (lDepth > rDepth)
            return (lDepth + 1);
        else
            return (rDepth + 1);
    }

    /**
     * Returns the smallest element within the tree.
     * @return the smallest element within the tree
     */
    public ShipLocation smallestElement(){
        return smallestElement(root);
    }

    protected ShipLocation smallestElement(Node<ShipLocation> node) {
        if (node == null)
            return null;

        Node<ShipLocation> auxNode;
        for(auxNode = node; auxNode.getLeft() !=  null; auxNode = auxNode.getLeft());

        return auxNode.getShipLocation();
    }

    /*
     * Returns an iterable collection of elements of the tree, reported in in-order.
     * @return iterable collection of the tree's elements reported in in-order
     */
    public Iterable<ShipLocation> inOrder(){
        List<ShipLocation> snapshot = new ArrayList<>();
        if (root!=null)
            inOrderSubtree(root, snapshot);   // fill the snapshot recursively
        return snapshot;
    }
    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an in-order traversal
     * @param node       Node serving as the root of a subtree
     * @param snapshot  a list to which results are appended
     */
    private void inOrderSubtree(Node<ShipLocation> node, List<ShipLocation> snapshot) {
        if (node == null)
            return;
        inOrderSubtree(node.getLeft(), snapshot);
        snapshot.add(node.getShipLocation());
        inOrderSubtree(node.getRight(), snapshot);
    }
    /**
     * Returns an iterable collection of elements of the tree, reported in pre-order.
     * @return iterable collection of the tree's elements reported in pre-order
     */
    public Iterable<ShipLocation> preOrder(){
        List<ShipLocation> snapshot = new ArrayList<>();
        if (root!=null)
            preOrderSubtree(root, snapshot);   // fill the snapshot recursively
        return snapshot;
    }
    /**
     * Adds elements of the subtree rooted at Node node to the given
     * snapshot using an pre-order traversal
     * @param node       Node serving as the root of a subtree
     * @param snapshot  a list to which results are appended
     */
    private void preOrderSubtree(Node<ShipLocation> node, List<ShipLocation> snapshot) {
        if(node == null)
            return;

        snapshot.add(node.getShipLocation());
        preOrderSubtree(node.getLeft(), snapshot);
        preOrderSubtree(node.getRight(), snapshot);
    }
    /**
     * Returns an iterable collection of elements of the tree, reported in post-order.
     * @return iterable collection of the tree's elements reported in post-order
     */
    public Iterable<ShipLocation> posOrder(){
        List<ShipLocation> snapshot = new ArrayList<>();
        if (root!=null)
            posOrderSubtree(root, snapshot);
        return snapshot;
    }
    /**
     * Adds positions of the subtree rooted at Node node to the given
     * snapshot using an post-order traversal
     * @param node       Node serving as the root of a subtree
     * @param snapshot  a list to which results are appended
     */
    private void posOrderSubtree(Node<ShipLocation> node, List<ShipLocation> snapshot) {
        if(node == null)
            return;
        posOrderSubtree(node.getLeft(), snapshot);
        posOrderSubtree(node.getRight(), snapshot);
        snapshot.add(node.getShipLocation());
    }

    /*
     * Returns a map with a list of nodes by each tree level.
     * @return a map with a list of nodes by each tree level
     */
    public Map<Integer,List<ShipLocation>> nodesByLevel(){
        Map<Integer, List<ShipLocation>> nodesBylevel = new HashMap<>();
        processBstByLevel(root, nodesBylevel, 0);
        return nodesBylevel;
    }

    private void processBstByLevel(Node<ShipLocation> node, Map<Integer,List<ShipLocation>> result, int level){
        if(node == null)
            return;

        if(result.get(level) == null)
            result.put(level, new ArrayList<>());

        result.get(level).add(node.getShipLocation());
        processBstByLevel(node.getLeft(), result, level + 1);
        processBstByLevel(node.getRight(), result, level + 1);
    }

//#############################################

    /**
     * This method allows to obtain the list of the ship's positional messages over a period of time
     * @param initialDate initial date of positional messages
     * @param finalDate final date of positional messages
     * @return list with the respective positional messages
     */
    public List<String> getPositionalMessages(Date initialDate, Date finalDate){

        Iterable<ShipLocation> bstInOrder = inOrder();
        Iterator<ShipLocation> iterator = bstInOrder.iterator();

        List<String> positionalMessages = new ArrayList<>();

        ShipLocation aux = null;

        if(iterator.hasNext()){
            aux = iterator.next();
        }

        while(iterator.hasNext() && !(aux.getMessageTime().after(finalDate))) {
            if(aux.getMessageTime().after(initialDate) && aux.getMessageTime().before(finalDate) || aux.getMessageTime().equals(initialDate) || aux.getMessageTime().equals(finalDate)){
                positionalMessages.add(aux.toString());
            }
            aux = iterator.next();
        }

        if(aux.getMessageTime().after(initialDate) && aux.getMessageTime().before(finalDate) || aux.getMessageTime().equals(initialDate) || aux.getMessageTime().equals(finalDate)){
            positionalMessages.add(aux.toString());
        }


        return positionalMessages;
    }

    /**
     * returns to first ship location
     * @return returns to first ship location
     */
    private ShipLocation startShipLocation(){
        Iterator<ShipLocation> shipLocations = inOrder().iterator();
        return shipLocations.next();
    }

    /**
     * returns to last ship location
     * @return returns to last ship location
     */
    private ShipLocation endShipLocation(){
        Iterator<ShipLocation> shipLocations = inOrder().iterator();
        ShipLocation shipLocation = null;
        while (shipLocations.hasNext()){
            shipLocation = shipLocations.next();
        }
        return shipLocation;
    }

    /**
     * returns date of first ship location
     * @return returns date of first ship location
     */
    public Date getStartBase() {
        return startShipLocation().getMessageTime();
    }

    /**
     * returns date of last ship location
     * @return returns date of last ship location
     */
    public Date getEndBase() {
        return endShipLocation().getMessageTime();
    }

    /**
     * returns the total number of moves made by a ship in a voyage
     * @return returns the total number of moves made by a ship in a voyage
     */
    public int getTotalMovements() {
        return this.size()-1;
    }

    /**
     * returns the total time spent on a voyage taken by a ship
     * @return returns the total time spent on a voyage taken by a ship
     */
    public String getTotalMovementsTime() {
        Iterator<ShipLocation> shipLocationIterator = inOrder().iterator();
        double sum = 0, date1 = 0, date2 = 0;
        ShipLocation firstLocation = shipLocationIterator.next();
        while (shipLocationIterator.hasNext()){
            ShipLocation secondLocation = shipLocationIterator.next();
            date1 = transformInSeconds(firstLocation.getMessageTime().getHours(), firstLocation.getMessageTime().getMinutes(), firstLocation.getMessageTime().getSeconds());
            date2 = transformInSeconds(secondLocation.getMessageTime().getHours(), secondLocation.getMessageTime().getMinutes(), secondLocation.getMessageTime().getSeconds());
            if (date1 > date2) sum += date1-date2;
            else sum += date2 - date1;
            firstLocation = secondLocation;
        }
        return transformInHours(sum);
    }

    /**
     * turn the time into seconds
     * @param hour the hours
     * @param minutes the minutes
     * @param seconds the seconds
     * @return the time in seconds
     */
    private double transformInSeconds(int hour, int minutes, int seconds){
        return hour*3600+minutes*60+seconds;
    }

    /**
     * turns time in seconds into hours, minutes and seconds
     * @param totalTime the total time in seconds
     * @return a String with the following format: HH:MM:SS
     */
    private String transformInHours(double totalTime){
        int hour = (int)totalTime/3600;
        totalTime%=3600;
        int minutes = (int) totalTime/60;
        totalTime%=60;
        int seconds = (int) totalTime;

        return hour + ":" + minutes + ":" + seconds;
    }

    /**
     * returns to the highest ground speed reached by ship
     * @return the highest ground speed reached by ship
     */
    public double getMaximumSog() {
        Iterable<ShipLocation> shipLocations = inOrder();
        double maxSog = 0;
        for (ShipLocation s : shipLocations){
            if (s.getSOG() > maxSog) maxSog = s.getSOG();
        }
        return maxSog;
    }

    /**
     * returns to the average speed over ground reached by ship
     * @return the average speed over ground reached by ship
     */
    public double getMeanSog() {
        Iterable<ShipLocation> shipLocations = inOrder();
        double meanSog = 0, size=0;
        for (ShipLocation s : shipLocations){
            meanSog+=s.getSOG();
            size++;
        }
        return meanSog/size;
    }

    /**
     * returns the longest course on the ground hit by a ship
     * @return the longest course on the ground hit by a ship
     */
    public double getMaximumCog() {
        Iterable<ShipLocation> shipLocations = inOrder();
        double maxCog = 0;
        for (ShipLocation s : shipLocations){
            if (s.getCOG() > maxCog) maxCog = s.getCOG();
        }
        return maxCog;
    }

    /**
     * returns the middle course over the ground hit by a ship
     * @return the middle course over the ground hit by a ship
     */
    public double getMeanCog() {
        Iterable<ShipLocation> shipLocations = inOrder();
        double meanCog = 0;
        for (ShipLocation s : shipLocations){
            meanCog+=s.getCOG();
        }
        return meanCog/size();
    }

    /**
     returns the latitude of the departure port of the voyage made by the ship
     * @return the latitude of the departure port of the voyage made by the ship
     */
    public String getLatitudeDeparture() {

        return startShipLocation().getLatitude();
    }

    /**
     * returns the longitude of the departure port of the voyage made by the ship
     * @return the longitude of the departure port of the voyage made by the ship
     */
    public String getLongitudeDeparture() {

        return startShipLocation().getLongitude();
    }

    /**
     * returns the latitude of the port of arrival of the voyage made by the ship
     * @return the latitude of the port of arrival of the voyage made by the ship
     */
    public String getArrivalLatitude() {
        return endShipLocation().getLatitude();
    }

    /**
     * returns the longitude of the port of arrival of the voyage made by the ship
     * @return the longitude of the port of arrival of the voyage made by the ship
     */
    public String getArrivalLongitude() {
        return endShipLocation().getLongitude();
    }

    /**
     * returns the total distance traveled by a ship on a voyage
     * @return the total distance traveled by a ship on a voyage
     */
    public double getTravelledDistance() {
        Iterator<ShipLocation> shipLocationIterator = inOrder().iterator();
        double sum = 0;
        ShipLocation firstLocation = shipLocationIterator.next();
        while (shipLocationIterator.hasNext()){
            ShipLocation secondLocation = shipLocationIterator.next();

            if (firstLocation.getLatitude().equals("not defined") || firstLocation.getLongitude().equals("not defined") || secondLocation.getLatitude().equals("not defined") || secondLocation.getLongitude().equals("not defined")){
                sum += 0;
            }
            else
            {
                sum += calculateDistance(Double.parseDouble(firstLocation.getLatitude()), Double.parseDouble(firstLocation.getLongitude()), Double.parseDouble(secondLocation.getLatitude()), Double.parseDouble(secondLocation.getLongitude()));
            }

            firstLocation = secondLocation;
        }
        return sum;
    }

    /**
     * returns the delta distance traveled by ship, that is, the distance between the departure point and the arrival point of the voyage
     * @return the delta distance traveled by ship
     */
    public double getDeltaDistance() {
        if (getArrivalLongitude().equals("not defined") || getArrivalLatitude().equals("not defined") || getLongitudeDeparture().equals("not defined") || getLatitudeDeparture().equals("not defined")){
            return 0;
        }
       else return calculateDistance(Double.parseDouble(getLatitudeDeparture()), Double.parseDouble(getLongitudeDeparture()), Double.parseDouble(getArrivalLatitude()), Double.parseDouble(getArrivalLongitude()));
    }

    /**
     * calculates the distance between two points, given their geographic coordinates
     * @param departureLatitude the departure latitude
     * @param departureLongitude the departure longitude
     * @param arrivalLatitude the arrival latitude
     * @param arrivalLongitude the arrival longitude
     * @return the distance between the two coordinates
     */
    private double calculateDistance(double departureLatitude, double departureLongitude, double arrivalLatitude, double arrivalLongitude){

        int earthRadius = 6371000;

        double initialLatitude= toRadian(departureLatitude);
        double initialLongitude= toRadian(departureLongitude);
        double endLatitude= toRadian(arrivalLatitude);
        double endLongitude= toRadian(arrivalLongitude);

        double variationOfLatitude = Math.abs(endLatitude-initialLatitude);

        double variationOfLongitude = Math.abs(endLongitude-initialLongitude);

        double a = Math.sin(variationOfLatitude/2) * Math.sin(variationOfLatitude/2) + Math.cos(initialLatitude)* Math.cos(endLatitude) * Math.sin(variationOfLongitude/2) * Math.sin(variationOfLongitude/2);

        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));


        return earthRadius*b;
    }

    /**
     * converts an angle in degrees to radian
     * @param degree the angle in degrees
     * @return the angle in radian
     */
    private double toRadian(double degree){
        return (degree*Math.PI)/180;
    }






} //----------- end of BST class -----------

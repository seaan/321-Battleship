/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

/**
 *
 * @author Sean Widmier, Kyle Daigle, Kelly Manley, Robert Womack
 */
public class Ship {

        private String shipType;
        private int shipLength;
        private int hitCount;
        private boolean isSunk;

        private Position startPosition;
        private Position endPosition;

        Orientation orientation;

        private enum Orientation {
                HORIZONTAL, VERTICAL;
        }

        protected Ship(String type, int length) {
                shipType = type;
                shipLength = length;
                hitCount = 0;
                isSunk = false;

        }

        protected String getType() {
                return shipType;
        }

        protected int getLength() {
                return shipLength;
        }

        protected Boolean getStatus() {
                return isSunk;
        }

        protected void incrementHitCount() {
                hitCount++;

                if (hitCount == shipLength) {
                        isSunk = true;

                        OceanGrid og = OceanGrid.getInstance();
                        og.incrementSunkCount();
                }
        }

        /**
         * Sets the start and end positions of a ship, then fills in the other
         * necessary spaces.
         *
         * @param start The new "start" position of the ship, usually the front.
         * @param end The new "end" position of the ship, usually the back.
         */
        protected void setPosition(Position start, Position end) {
                OceanGrid og = OceanGrid.getInstance();

                startPosition = start;
                endPosition = end;

                if (this.startPosition.getX() == this.endPosition.getX()) {
                        orientation = Orientation.VERTICAL;
                } else if (this.startPosition.getY() == this.endPosition.getY()) {
                        orientation = Orientation.HORIZONTAL;
                }

                switch (orientation) {
                        case VERTICAL:
                                if (endPosition.getY() < endPosition.getY()) {
                                        for (int i = startPosition.getY(); i <= endPosition.getY(); i++) {
                                                og.setShip(startPosition.getX(), i);
                                        }
                                } else {
                                        for (int i = endPosition.getY(); i <= startPosition.getY(); i++) {
                                                og.setShip(startPosition.getX(), i);
                                        }
                                }
                        case HORIZONTAL:
                                if (startPosition.getX() < endPosition.getX()) {
                                        for (int i = startPosition.getX(); i <= endPosition.getX(); i++) {
                                                og.setShip(i, startPosition.getY());
                                        }
                                } else {
                                        for (int i = endPosition.getX(); i <= startPosition.getX(); i++) {
                                                og.setShip(i, startPosition.getY());
                                        }
                                }
                }
        }

        protected boolean checkPosition(int x, int y) {
                OceanGrid og = OceanGrid.getInstance();
                if ((startPosition.getX() <= x && x <= endPosition.getX())
                        && ((startPosition.getY() <= y && y <= endPosition.getY()))) {
                        incrementHitCount();
                        return true;
                } else if ((startPosition.getX() >= x && x >= endPosition.getX())
                        && ((startPosition.getY() >= y && y >= endPosition.getY()))) {
                        incrementHitCount();
                        return true;
                } else {
                        return false;
                }
        }

        protected void printShip() {
                System.out.println("Type: " + this.shipType);
                System.out.println("Length: " + this.shipLength);
                System.out.println("Hit count: " + this.hitCount);
                System.out.println("isSunk: " + this.isSunk);
        }
}

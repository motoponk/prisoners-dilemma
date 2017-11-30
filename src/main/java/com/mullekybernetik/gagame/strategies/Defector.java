package com.mullekybernetik.gagame.strategies;

import com.mullekybernetik.gagame.match.Move;

public class Defector implements Strategy, Player {

    public Player instantiate() {
        return this;
    }

    public Move getMove() {
        return Move.DEFECT;
    }

    public void setOpponentsMove(Move m) {
    }

    @Override
    public String toString() {
        return "Defector";
    }

    @Override
    public boolean equals(Object other) { return other instanceof Defector; }

}

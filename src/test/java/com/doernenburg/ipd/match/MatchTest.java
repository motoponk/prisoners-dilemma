package com.doernenburg.ipd.match;

import com.doernenburg.ipd.strategies.basic.Cooperator;
import com.doernenburg.ipd.strategies.basic.Defector;
import com.doernenburg.ipd.strategies.Player;
import org.junit.Assert;
import org.junit.Test;

import static com.doernenburg.ipd.match.Match.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MatchTest {

    @Test
    public void shouldProvidePlayersWithResultsForSingleGame() {
        Player alice = mock(Player.class);
        when(alice.getMove()).thenReturn(Move.COOPERATE);

        Player bob = mock(Player.class);
        when(bob.getMove()).thenReturn(Move.DEFECT);

        Match match = new Match(1, 0);
        match.playMatch(alice, bob);

        verify(alice).setOpponentsMove(Move.DEFECT);
        verify(bob).setOpponentsMove(Move.COOPERATE);
    }

    @Test
    public void shouldAssignCorrectPointsForSuccessfulCooperation() {
        Match match = new Match(1, 0);
        match.playMatch(new Cooperator(), new Cooperator());
        Score score = match.getScore();

        assertEquals(POINTS_FOR_SUCCESSFUL_COOPERATION, score.a);
        assertEquals(POINTS_FOR_SUCCESSFUL_COOPERATION, score.b);
    }

    @Test
    public void shouldAssignCorrectPointsForSuccessfulBetrayalFirstPlayer() {
        Match match = new Match(1, 0);
        match.playMatch(new Cooperator(), new Defector());
        Score score = match.getScore();

        Assert.assertEquals(POINTS_FOR_UNSUCCESSFUL_COOPERATION, score.a);
        Assert.assertEquals(POINTS_FOR_SUCCESSFUL_BETRAYAL, score.b);
    }

    @Test
    public void shouldAssignCorrectPointsForSuccessfulBetrayalSecondPlayer() {
        Match match = new Match(1, 0);
        match.playMatch(new Defector(), new Cooperator());
        Score score = match.getScore();

        Assert.assertEquals(POINTS_FOR_SUCCESSFUL_BETRAYAL, score.a);
        Assert.assertEquals(POINTS_FOR_UNSUCCESSFUL_COOPERATION, score.b);
    }

    @Test
    public void shouldAssignCorrectPointsForMutualBetrayal() {
        Match match = new Match(1, 0);
        match.playMatch(new Defector(), new Defector());
        Score score = match.getScore();

        Assert.assertEquals(POINTS_FOR_MUTUAL_BETRAYAL, score.a);
        Assert.assertEquals(POINTS_FOR_MUTUAL_BETRAYAL, score.b);
    }

    @Test
    public void shouldRunMultipleGamesAndReturnScore() {
        Match match = new Match(2, 0);
        Score score = match.playMatch(new Cooperator(), new Defector());

        assertEquals("Should have right number of points for cooperator", 0, score.a);
        Assert.assertEquals("Should have right number of points for defector", 2 * POINTS_FOR_SUCCESSFUL_BETRAYAL, score.b);
    }

}

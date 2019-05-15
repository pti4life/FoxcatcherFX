package org.unideb;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    private boolean containsForEnabledOperators(List<String> source, List<String> target) {
        for (String str:source) {
            if (!target.contains(str)) {
                return false;
            }
        }
        return true;
    }


    private boolean steppingTestHelper(int fromX, int fromY, int ToX, int ToY,State state,String figureFrom) {
        String figureTo="";
        if(!state.enabledOperators(fromX,fromY).isEmpty()) {
            state.stepping(ToX,ToY);
        }
        List<String> li=state.getStateOfGame();
        return li.get((ToX+1)*8-(8-ToY)).equals(figureFrom) && li.get((fromX+1)*8-(8-fromY)).equals(figureTo) ;

    }


    @Test
    void testStepping() {
        State state=new State(new int[][] {
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,4,0,4,0,0}, });

        assertTrue(steppingTestHelper(7,1,6,0,state,"4"));
        //assertTrue(state.getStateOfGame().get());
        //steppingTestHelper(6,0,6,0,state);
    }

    @Test
    void testEnabledOperators() {
        State state=new State(new int[][] {
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,4,0,4,0,0}, });
        assertTrue(containsForEnabledOperators(List.of("btn11","btn13"),state.enabledOperators(0,2)));
        assertTrue(containsForEnabledOperators(List.of(),state.enabledOperators(0,2)));
        assertTrue(containsForEnabledOperators(List.of("btn62","btn64"),state.enabledOperators(7,3)));
        assertTrue(containsForEnabledOperators(List.of(),state.enabledOperators(7,3)));

        state=new State(new int[][] {
                {0,0,0,0,0,0,0,0},
                {0,0,0,3,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,0,0,0,0,4},
                {4,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}, });


        assertTrue(containsForEnabledOperators(List.of("btn22","btn24","btn02","btn04"),state.enabledOperators(1,3)));
        assertTrue(containsForEnabledOperators(List.of(),state.enabledOperators(6,0)));
        assertTrue(containsForEnabledOperators(List.of("btn46"),state.enabledOperators(5,7)));
        state=new State(new int[][] {
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,3,0,0,0,0,0},
                {0,4,0,0,0,0,0,4},
                {4,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}, });

        assertTrue(containsForEnabledOperators(List.of("btn53","btn31","btn33"),state.enabledOperators(4,2)));

    }

    @Test
    void testIsGoal() {


    }
}
import org.junit.jupiter.api.Test;
import org.unideb.State;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class StateTest {

    private boolean containsForEnabledOperators(List<String> source, List<String> target) {
        for (String str:source) {
            if (!target.contains(str)) {
                return false;
            }
        }
        return true;
    }


    private boolean steppingTestHelper(int fromX, int fromY, int ToX, int ToY, State state, String figureFrom) {
        String figureTo="";
        if(!state.enabledOperators(fromX,fromY).isEmpty()) {
            state.stepping(ToX,ToY);
        }
        List<String> li=state.stateToListGetter();
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
        assertFalse(steppingTestHelper(6,0,5,1,state,"4"));

        assertTrue(steppingTestHelper(0,2,1,3,state,"3"));
        assertFalse(steppingTestHelper(1,3,2,2,state,"3"));

        assertTrue(steppingTestHelper(6,0,5,1,state,"4"));
        assertTrue(steppingTestHelper(1,3,0,5,state,"3"));

        assertTrue(steppingTestHelper(7,3,6,4,state,"4"));
        assertTrue(steppingTestHelper(0,5,1,3,state,"3"));
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
    void testIsGoalFox() {
        State state=new State(new int[][] {
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,4,0,4,0,0}, });


        assertFalse(state.isGoalFox());

        state=new State(new int[][] {
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,4,0},
                {0,0,0,0,0,3,0,0},
                {4,0,0,0,0,0,0,0},
                {0,0,0,4,0,0,0,0}, });


        assertFalse(state.isGoalFox());

        state=new State(new int[][] {
                {3,0,0,0,0,0,0,0},
                {0,4,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {4,0,0,0,0,0,0,0},
                {0,0,0,4,0,0,0,0}, });

        assertFalse(state.isGoalFox());

        state=new State(new int[][] {
                {0,0,0,0,0,0,0,0},
                {0,4,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,4,0,0},
                {4,0,0,0,0,0,0,0},
                {0,0,0,3,0,0,0,0}, });

        assertTrue(state.isGoalFox());

    }

    @Test
    void testIsGoalDog() {
            State state=new State(new int[][] {
                    {0,0,3,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,4,0,4,0,4,0,0}, });


            assertFalse(state.isGoalDog());

            state=new State(new int[][] {
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,4,0},
                    {0,0,0,0,0,3,0,0},
                    {4,0,0,0,0,0,0,0},
                    {0,0,0,4,0,0,0,0}, });


            assertFalse(state.isGoalDog());

            state=new State(new int[][] {
                    {3,0,0,0,0,0,0,0},
                    {0,4,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {4,0,0,0,0,0,0,0},
                    {0,0,0,4,0,0,0,0}, });

            assertTrue(state.isGoalDog());

            state=new State(new int[][] {
                    {0,0,0,0,0,0,0,0},
                    {0,4,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,4,0,0},
                    {4,0,0,0,0,0,0,0},
                    {0,0,0,3,0,0,0,0}, });

            assertFalse(state.isGoalDog());
    }

    @Test
    void testIsValid() {

         assertThrows(IllegalArgumentException.class,()->new State(new int[][] {
                 {0,0,3,0,0,0,0,0},
                 {0,0,0,0,0,0,0,0},
                 {0,0,0,0,0,0,0,0},
                 {0,0,0,0,0,0,0,0},
                 {0,0,0,0,0,0},
                 {0,0,0,0,0,0,0,0},
                 {0,0,0,0,0,0,0,0},
                 {0,4,0,4,0,4,0,0}, }));


        assertThrows(IllegalArgumentException.class,()->new State(new int[][] {
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,12,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,4,0,4,0,0}, }));

        assertThrows(IllegalArgumentException.class,()->new State(null));

        assertThrows(IllegalArgumentException.class,()->new State(new int[][] {
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},}));

        assertDoesNotThrow(()->new State(new int[][] {
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,3,0,0,0,0,0},
                {0,4,0,0,0,0,0,4},
                {4,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}, }));

    }

}
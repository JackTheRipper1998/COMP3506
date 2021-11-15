import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FactChecker {

    /**
     * Checks if a list of facts is internally consistent. 
     * That is, can they all hold true at the same time?
     * Or are two (or potentially more) facts logically incompatible?
     * 
     * @param facts list of facts to check consistency of
     * @return true if all the facts are internally consistent, otherwise false.
     */
    public static boolean areFactsConsistent(List<Fact> facts) {
        // TODO: implement this!
        HashSet<String> persons = new HashSet<>(); // a hashset represents all persons attended the party
        HashMap<String, Vertex> personToVertex = new HashMap<>(); // a hashmap match each person to a person vertex
        HashSet<String> personsBefore = new HashSet<>(); // a hashset represents persons have left

        // collect persons who attended the party && match each person to a person vertex
        for (Fact fact : facts) {
            persons.add(fact.getPersonA());
            persons.add(fact.getPersonB());

            if (personToVertex.get(fact.getPersonA()) == null) {
                personToVertex.put(fact.getPersonA(), new Vertex(fact.getPersonA()));
            }
            if (personToVertex.get(fact.getPersonB()) == null) {
                personToVertex.put(fact.getPersonB(), new Vertex(fact.getPersonB()));
            }
        }

        // initialise time.
        int initialTime = 0;

        // check if facts make sense.
        for (Fact fact : facts) {
            int timeA = personToVertex.get(fact.getPersonA()).time; // get personA time of the fact.
            int timeB = personToVertex.get(fact.getPersonB()).time; // get personB time of the fact.

            // check fact TYPE_ONE.
            if (fact.getType() == Fact.FactType.TYPE_ONE) {
                if (timeA == Integer.MIN_VALUE && timeB == Integer.MIN_VALUE) {
                    personToVertex.get(fact.getPersonA()).time = initialTime;
                    personToVertex.get(fact.getPersonB()).time = initialTime + 1;
                } else if (timeA == Integer.MIN_VALUE && timeB != Integer.MIN_VALUE) {
                    personToVertex.get(fact.getPersonA()).time = timeB - 1;
                } else if (timeA != Integer.MIN_VALUE && timeB == Integer.MIN_VALUE) {
                    personToVertex.get(fact.getPersonB()).time = timeA + 1;
                } else {
                    // if both person have been recorded then check their time.
                    if (personsBefore.contains(fact.getPersonA()) || personsBefore.contains(fact.getPersonB())) {
                        if (timeA >= timeB) {
                            return false;
                        }
                    } else {
                        personToVertex.get(fact.getPersonB()).time = timeA + 1;
                    }
                }

                personsBefore.add(fact.getPersonA());

            } else {
                // check fact TYPE_TWO.
                if (timeA == Integer.MIN_VALUE && timeB == Integer.MIN_VALUE) {
                    personToVertex.get(fact.getPersonA()).time = initialTime;
                    personToVertex.get(fact.getPersonB()).time = initialTime;
                } else if (timeA == Integer.MIN_VALUE && timeB != Integer.MIN_VALUE) {
                    personToVertex.get(fact.getPersonA()).time = timeB;
                } else if (timeA != Integer.MIN_VALUE && timeB == Integer.MIN_VALUE) {
                    personToVertex.get(fact.getPersonB()).time = timeA;
                } else {
                    // if both person have been recorded then check their time.
                    if (personsBefore.contains(fact.getPersonA()) || personsBefore.contains(fact.getPersonB())) {
                        if (timeA != timeB) {
                            return false;
                        }
                    }
                }
                if (personsBefore.contains(fact.getPersonA()) || personsBefore.contains(fact.getPersonB())) {
                    personsBefore.add(fact.getPersonA());
                    personsBefore.add(fact.getPersonB());
                }
            }
        }

        return true;
    }

    static class Vertex {
        String person;
        int time;

        Vertex(String person) {
            this.person = person;
            this.time = Integer.MIN_VALUE;
        }
    }

}

import java.util.*;

public class ContactTracer {
    private HashMap<String, HashSet<Edge>> graph; // a hashmap shows each person and their contact information.
    private HashMap<String, Vertex> personToVertex; // a hashmap matches each person to person vertex.

    /**
     * Initialises an empty ContactTracer with no populated contact traces.
     */
    public ContactTracer() {
        // TODO: implement this!
        graph = new HashMap<>();
        personToVertex = new HashMap<>();
    }

    /**
     * Initialises the ContactTracer and populates the internal data structures
     * with the given list of contract traces.
     * 
     * @param traces to populate with
     * @require traces != null
     */
    public ContactTracer(List<Trace> traces) {
        // TODO: implement this!
        graph = new HashMap<>();
        personToVertex = new HashMap<>();

        // build graph
        for (Trace trace : traces) {
            addTrace(trace);
        }
    }

    /**
     * Adds a new contact trace to 
     * 
     * If a contact trace involving the same two people at the exact same time is
     * already stored, do nothing.
     * 
     * @param trace to add
     * @require trace != null
     */
    public void addTrace(Trace trace) {
        // TODO: implement this!
        String person1 = trace.getPerson1();
        String person2 = trace.getPerson2();
        int time = trace.getTime();

        HashSet<Edge> edges = graph.computeIfAbsent(person1, (k) -> new HashSet<>());

        // if there is already exist a trace which has same persons and contact time then do not add it.
        for (Edge edge : edges) {
            if (edge.personTo.equals(person2) && edge.time == time) {
                return;
            }
        }

        if (graph.containsKey(person2)) {
            for (Edge edge : graph.get(person2)) {
                if (edge.personTo.equals(person1) && edge.time == time) {
                    return;
                }
            }
        }

        // create vertex for each person
        if (personToVertex.get(person1) == null) {
            personToVertex.put(person1, new Vertex(person1));
        }

        if (personToVertex.get(person2) == null) {
            personToVertex.put(person2, new Vertex(person2));
        }

        edges.add(new Edge(person2, time));
    }


    /**
     * Gets a list of times that person1 and person2 have come into direct 
     * contact (as per the tracing data).
     *
     * If the two people haven't come into contact before, an empty list is returned.
     * 
     * Otherwise the list should be sorted in ascending order.
     * 
     * @param person1 
     * @param person2
     * @return a list of contact times, in ascending order.
     * @require person1 != null && person2 != null
     */
    public List<Integer> getContactTimes(String person1, String person2) {
        // TODO: implement this!
        // an arraylist to collect directed contact time between both persons
        ArrayList<Integer> contactTimes = new ArrayList<>();

        // if the graph does not contain both person then return a empty list.
        if (!graph.containsKey(person1) && !graph.containsKey(person2))
            return contactTimes;

        // if the graph contain person1 then check if person1 contain an edge to person2,
        // if contain then add into list.
        if (graph.containsKey(person1)) {
            for (Edge edge : graph.get(person1)) {
                if (edge.personTo.equals(person2)) {
                    if (!contactTimes.contains(edge.time))
                        contactTimes.add(edge.time);
                }
            }
        }

        // if the graph contain person2 then check if person1 contain an edge to person1,
        // if contain then add into list.
        if (graph.containsKey(person2)) {
            for (Edge edge : graph.get(person2)) {
                if (edge.personTo.equals(person1)) {
                    if (!contactTimes.contains(edge.time))
                        contactTimes.add(edge.time);
                }
            }
        }

        // sort contact times by ascending order.
        Collections.sort(contactTimes);
        return contactTimes;
    }

    /**
     * Gets all the people that the given person has been in direct contact with
     * over the entire history of the tracing dataset.
     * 
     * @param person to list direct contacts of
     * @return set of the person's direct contacts
     */
    private HashSet<Edge> contactEdges; // a hashset contain all edges out from the person input below.
    public Set<String> getContacts(String person) {
        // TODO: implement this!
        // a hashset contain all persons who have directed contact with person input.
        HashSet<String> contacts = new HashSet<>();
        contactEdges = new HashSet<>();

        for (String vertex : graph.keySet()) {
            if (vertex.equals(person)) {
                for (Edge edge : graph.get(person)) {
                    contacts.add(edge.personTo);
                    contactEdges.add(edge);
                }
            } else {
                for (Edge edge : graph.get(vertex)) {
                    if (edge.personTo.equals(person)) {
                        contacts.add(vertex);
                        contactEdges.add(edge);
                    }
                }
            }
        }

        return contacts;
    }

    /**
     * Gets all the people that the given person has been in direct contact with
     * at OR after the given timestamp (i.e. inclusive).
     * 
     * @param person to list direct contacts of
     * @param timestamp to filter contacts being at or after
     * @return set of the person's direct contacts at or after the timestamp
     */
    public Set<String> getContactsAfter(String person, int timestamp) {
        // TODO: implement this!
        // a hashset which contain all person which get contact after timestamp.
        HashSet<String> contactsAfter = new HashSet<>();

        getContacts(person);

        // search persons who satisfy the property.
        for (Edge edge : contactEdges) {
            if (edge.time >= timestamp) {
                contactsAfter.add(edge.personTo);
            }
        }

        return contactsAfter;
    }

    /**
     * Initiates a contact trace starting with the given person, who
     * became contagious at timeOfContagion.
     * 
     * Note that the return set shouldn't include the original person the trace started from.
     * 
     * @param person to start contact tracing from
     * @param timeOfContagion the exact time person became contagious
     * @return set of people who may have contracted the disease, originating from person
     */
    public Set<String> contactTrace(String person, int timeOfContagion) {
        // TODO: implement this!
        HashSet<String> contactTrace = new HashSet<>();
        dijkstra(graph, person, timeOfContagion);

        for (String candidate : personToVertex.keySet()) {
            if (personToVertex.get(candidate).traced) {
                contactTrace.add(candidate);
            }
        }

        return contactTrace;
    }

    /**
     * calculate contact time on each person vertex
     * @param graph a hashmap shows each person and their contact information.
     * @param source start person
     * @param timeOfContagion time became contagious
     */
    private void dijkstra (HashMap<String, HashSet<Edge>> graph, String source, int timeOfContagion) {
        PriorityQueue<Priority<Vertex>> pq = new PriorityQueue<>();
        personToVertex.get(source).time = timeOfContagion;

        for (Edge edge : graph.get(source)) {
            if (edge.time >= timeOfContagion) {
                pq.add(new Priority<>(personToVertex.get(edge.personTo).time, personToVertex.get(edge.personTo)));
                personToVertex.get(edge.personTo).traced = true;
            }
        }

        while(!pq.isEmpty()) {
            Vertex u = pq.poll().element;

            for (Edge v : graph.computeIfAbsent(u.person, (k) -> new HashSet<>())) {
                int candidateTime = u.time + 60;

                if (v.time >= candidateTime) {
                    pq.add(new Priority<>(v.time,personToVertex.get(v.personTo)));
                    personToVertex.get(v.personTo).time = v.time;
                    personToVertex.get(v.personTo).traced = true;
                }
            }
        }
    }

    static class Edge {
        String personTo;
        int time;

        Edge(String personTo, int time) {
            this.personTo = personTo;
            this.time = time;
        }
    }

    static class Vertex {
        String person;
        int time;
        Boolean traced;

        Vertex(String person) {
            this.person = person;
            this.time = 0;
            this.traced = false;
        }
    }

    static class Priority<T> implements Comparable<Priority<T>> {
        public int priority;
        public T element;

        public Priority(int priority, T element) {
            this.priority = priority;
            this.element = element;
        }

        @Override
        public int compareTo(Priority<T> that) {
            return Integer.compare(priority, that.priority);
        }
    }
}

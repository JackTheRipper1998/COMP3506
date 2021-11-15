import java.util.*;

public class ErdosNumbers {
    /**
     * String representing Paul Erdos's name to check against
     */
    public static final String ERDOS = "Paul Erdös";
    private HashMap<String, Vertex> authorToVertex; // a hashmap contain authors and authors' Vertex.
    private HashMap<String, HashSet<String>> authorPapers; // a hashmap shows papers written by author.
    private HashMap<String, HashSet<String>> authorCollaborators; // a hashmap represent all author's collaborators.
    private HashMap<String, HashSet<String>> paperAuthors; // a hashmap stand for authors of a paper.
    private HashMap<String, ArrayList<String>> authorCoAuthors; // author's collaborators with duplicates

    /**
     * Initialises the class with a list of papers and authors.
     *
     * Each element in 'papers' corresponds to a String of the form:
     * 
     * [paper name]:[author1][|author2[|...]]]
     *
     * Note that for this constructor and the below methods, authors and papers
     * are unique (i.e. there can't be multiple authors or papers with the exact same name or title).
     * 
     * @param papers List of papers and their authors
     */
    public ErdosNumbers(List<String> papers) {
        // TODO: implement this
        authorToVertex = new HashMap<>();
        authorPapers = new HashMap<>();
        authorCollaborators = new HashMap<>();
        paperAuthors = new HashMap<>();
        authorCoAuthors = new HashMap<>();

        // build graph
        for (String paper : papers) {
            String[] split = paper.split(":"); // split paper and authors.
            String[] authors = split[1].split("\\|"); // split authors into each author.
            String paperName = split[0];
            HashSet<String> authorList = paperAuthors.computeIfAbsent(paperName, (k) -> new HashSet<>());

            for (String author : authors) {
                HashSet<String> paperList = authorPapers.computeIfAbsent(author, (k) -> new HashSet<>());
                HashSet<String> collaboratorList = authorCollaborators
                        .computeIfAbsent(author, (k) -> new HashSet<>());
                ArrayList<String> coAuthorsList = authorCoAuthors
                        .computeIfAbsent(author, (k) -> new ArrayList<>());

                authorList.add(author);
                paperList.add(split[0]);

                // add author to Vertex
                if (authorToVertex.get(author) == null) {
                    authorToVertex.put(author, new Vertex(author));
                }

                for(String authorhaha : authors) {
                    if (!authorhaha.equals(author)) {
                        collaboratorList.add(authorhaha);
                        coAuthorsList.add(authorhaha);
                    }
                }
            }
        }

        // calculate ErdosNumber on each vertex.
        dijkstra(authorCollaborators, ERDOS);
        // calculate WeightedErdosNumber on each vertex
        weightedDijkstra(authorCollaborators, ERDOS);
    }

    /**
     * calculate how many papers have coAuthorships of two input authors
     * @param authorOne author one
     * @param authorTwo author two
     * @return the number of papers which have coAuthorships of two input authors
     */
    private int coAuthorshipsPaper (String authorOne, String authorTwo) {
        int coPaperNum = 0;
        for (String author : authorCoAuthors.get(authorOne)) {
            if (author.equals(authorTwo)) {
                coPaperNum++;
            }
        }
        return coPaperNum;
    }

    /**
     * calculate WeightedErdosNumber on each vertex
     * @param graph a hashmap represent all author's collaborators.
     * @param source Erdos
     */
    private void weightedDijkstra (HashMap<String, HashSet<String>> graph, String source) {
        authorToVertex.get(source).weightedErdosNum = 0.0;
        PriorityQueue<WeightedPriority<Vertex>> pq = new PriorityQueue<>();
        pq.add(new WeightedPriority<>(authorToVertex.get(source).weightedErdosNum, authorToVertex.get(source)));

        while(!pq.isEmpty()) {
            Vertex u = pq.poll().element;

            for (String v : graph.computeIfAbsent(u.author, (k) -> new HashSet<>())) {
                double candidateWeight;
                if (coAuthorshipsPaper(u.author, v) == 0) {
                    candidateWeight = 0;
                } else {
                    candidateWeight =  (1d/coAuthorshipsPaper(u.author, v));
                }
                double weightedErdosNumber = u.weightedErdosNum + candidateWeight;

                if (authorToVertex.get(v).weightedErdosNum > weightedErdosNumber) {
                    authorToVertex.get(v).weightedErdosNum = weightedErdosNumber;
                    pq.add(new WeightedPriority<>(authorToVertex.get(v).weightedErdosNum,authorToVertex.get(v)));
                    authorToVertex.get(v).pi = u;
                }
            }
        }
    }

    /**
     * calculate ErdosNumber on each vertex
     * @param graph a hashmap represent all author's collaborators.
     * @param source Erdos
     */
    private void dijkstra (HashMap<String, HashSet<String>> graph, String source) {
        authorToVertex.get(source).erdosNumber = 0;
        PriorityQueue<Priority<Vertex>> pq = new PriorityQueue<>();
        pq.add(new Priority<>(authorToVertex.get(source).erdosNumber, authorToVertex.get(source)));

        while(!pq.isEmpty()) {
            Vertex u = pq.poll().element;

            for (String v : graph.computeIfAbsent(u.author, (k) -> new HashSet<>())) {
                int erdosNumber = u.erdosNumber + 1;

                if (authorToVertex.get(v).erdosNumber > erdosNumber) {
                    authorToVertex.get(v).erdosNumber = erdosNumber;
                    pq.add(new Priority<>(authorToVertex.get(v).erdosNumber,authorToVertex.get(v)));
                    authorToVertex.get(v).pi = u;
                }
            }
        }
    }

    
    /**
     * Gets all the unique papers the author has written (either solely or
     * as a co-author).
     * 
     * @param author to get the papers for.
     * @return the unique set of papers this author has written.
     */
    public Set<String> getPapers(String author) {
        // TODO: implement this
        return authorPapers.get(author);
    }

    /**
     * Gets all the unique co-authors the author has written a paper with.
     *
     * @param author to get collaborators for
     * @return the unique co-authors the author has written with.
     */
    public Set<String> getCollaborators(String author) {
        // TODO: implement this
        return authorCollaborators.get(author);
    }

    /**
     * Checks if Erdos is connected to all other author's given as input to
     * the class constructor.
     * 
     * In other words, does every author in the dataset have an Erdos number?
     * 
     * @return the connectivity of Erdos to all other authors.
     */
    public boolean isErdosConnectedToAll() {
        // TODO: implement this
        boolean flag = true;

        for (String author : authorToVertex.keySet()) {
            if (authorToVertex.get(author).erdosNumber == Integer.MAX_VALUE) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Calculate the Erdos number of an author. 
     * 
     * This is defined as the length of the shortest path on a graph of paper 
     * collaborations (as explained in the assignment specification).
     * 
     * If the author isn't connected to Erdos (and in other words, doesn't have
     * a defined Erdos number), returns Integer.MAX_VALUE.
     * 
     * Note: Erdos himself has an Erdos number of 0.
     * 
     * @param author to calculate the Erdos number of
     * @return authors' Erdos number or otherwise Integer.MAX_VALUE
     */
    public int calculateErdosNumber(String author) {
        // TODO: implement this
        return authorToVertex.get(author).erdosNumber;
    }

    /**
     * Gets the average Erdos number of all the authors on a paper.
     * If a paper has just a single author, this is just the author's Erdos number.
     *
     * Note: Erdos himself has an Erdos number of 0.
     *
     * @param paper to calculate it for
     * @return average Erdos number of paper's authors
     */
    public double averageErdosNumber(String paper) {
        // TODO: implement this
        HashSet<String> authorList = paperAuthors.get(paper);
        int totolErdosNum = 0;

        for (String author : authorList) {
            totolErdosNum += authorToVertex.get(author).erdosNumber;
        }
        return (double) totolErdosNum/authorList.size();
    }

    /**
     * Calculates the "weighted Erdos number" of an author.
     * 
     * If the author isn't connected to Erdos (and in other words, doesn't have
     * an Erdos number), returns Double.MAX_VALUE.
     *
     * Note: Erdos himself has a weighted Erdos number of 0.
     * 
     * @param author to calculate it for
     * @return author's weighted Erdos number
     */
    public double calculateWeightedErdosNumber(String author) {
        // TODO: implement this
        return authorToVertex.get(author).weightedErdosNum;
    }

    static class Vertex {
        String author;
        int erdosNumber;
        double weightedErdosNum;
        Vertex pi;

        Vertex(String author) {
            this.author = author;
            this.erdosNumber = Integer.MAX_VALUE;
            this.weightedErdosNum = Double.MAX_VALUE;
            this.pi = null;
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

    static class WeightedPriority<T> implements Comparable<WeightedPriority<T>> {
        public double priority;
        public T element;

        public WeightedPriority(double priority, T element) {
            this.priority = priority;
            this.element = element;
        }

        @Override
        public int compareTo(WeightedPriority<T> that) {
            return Double.compare(priority, that.priority);
        }
    }


}

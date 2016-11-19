package com.akieus.elections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Results:
 * 44 places where UKIP was second after Labour
 With a threshold of  5.0, can win additional 0 seats with 0 votes
 With a threshold of 10.0, can win additional 1 seats with 3024 votes
 With a threshold of 15.0, can win additional 3 seats with 13303 votes
 With a threshold of 20.0, can win additional 5 seats with 25779 votes
 With a threshold of 25.0, can win additional 8 seats with 51196 votes
 With a threshold of 30.0, can win additional 19 seats with 172648 votes
 With a threshold of 35.0, can win additional 29 seats with 305492 votes
 With a threshold of 36.0, can win additional 33 seats with 357462 votes
 With a threshold of 37.0, can win additional 35 seats with 384242 votes
 With a threshold of 38.0, can win additional 35 seats with 384242 votes
 With a threshold of 39.0, can win additional 36 seats with 396719 votes
 With a threshold of 40.0, can win additional 37 seats with 411503 votes
 With a threshold of 45.0, can win additional 39 seats with 438847 votes
 With a threshold of 50.0, can win additional 40 seats with 455721 votes


 120 places where UKIP was second
 With a threshold of  5.0, can win additional 0 seats with 0 votes
 With a threshold of 10.0, can win additional 2 seats with 5836 votes
 With a threshold of 15.0, can win additional 6 seats with 27584 votes
 With a threshold of 20.0, can win additional 11 seats with 70388 votes
 With a threshold of 25.0, can win additional 17 seats with 131995 votes
 With a threshold of 30.0, can win additional 36 seats with 365030 votes
 With a threshold of 35.0, can win additional 58 seats with 701851 votes
 With a threshold of 36.0, can win additional 67 seats with 842576 votes
 With a threshold of 37.0, can win additional 75 seats with 978558 votes
 With a threshold of 38.0, can win additional 77 seats with 1017298 votes
 With a threshold of 39.0, can win additional 80 seats with 1067222 votes
 With a threshold of 40.0, can win additional 87 seats with 1205116 votes
 With a threshold of 45.0, can win additional 107 seats with 1635510 votes
 With a threshold of 50.0, can win additional 115 seats with 1824726 votes
 */
public class Main {
    private static final String[] HEADERS = new String[]{"forename", "surname", "candidateDescription",
            "constituencyName", "constituencyIndex", "votes", "voteShare", "voteChange", "isIncumbent", "dummy",
            "constituencyId", "regionId", "county", "region", "country", "constituencyType", "partyName",
            "partyNameAbrv"};

    public static void main(String[] args) throws IOException {
        ElectionResults electionResults = electionResults(args[0]);

        Map<Constituency, List<ElectionResult>> ukipSecond = electionResults.getResults().entrySet().stream()
                // .filter(e -> e.getValue().get(0).candidate.party.partyName.equals("Lab"))
                .filter(e -> e.getValue().get(1).candidate.party.partyName.equals("UKIP"))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        System.out.println(ukipSecond.size() + " places where UKIP was second");

        double[] voteDiffThresholds = new double[]{5.0, 10.0, 15.0, 20.0, 25.0, 30.0, 35.0, 36, 37, 38, 39, 40.0, 45.0, 50.0 };
        for (double voteDiffThreshold : voteDiffThresholds) {
            List<List<ElectionResult>> ukipClose = ukipSecond.entrySet().stream().map(e -> e.getValue())
                    .filter(l -> l.get(0).voteShare - l.get(1).voteShare < voteDiffThreshold).collect(Collectors.toList());
            int totalDiff = ukipClose.stream().mapToInt(l -> l.get(0).votes - l.get(1).votes).sum();

            System.out.println(String.format("With a threshold of %4.1f, can win additional %d seats with %d votes", voteDiffThreshold, ukipClose.size(), totalDiff));
        }
    }

    private static ElectionResults electionResults(String resultsFile) throws IOException {
        List<CSVRecord> csvRecords = readCsv(resultsFile);
        ElectionResults electionResults = new ElectionResults(2015, "UK 2015 General Elections");
        csvRecords.stream()
                .map(Main::map)
                .forEach(result -> electionResults.getResults().computeIfAbsent(result.constituency, (x) -> new LinkedList<>()).add(result));
        electionResults.getResults().entrySet().forEach(e -> Collections.sort(e.getValue()));
        return electionResults;
    }

    private static ElectionResult map(CSVRecord record) {
        Constituency constituency = new Constituency(Integer.parseInt(record.get("constituencyIndex")), record.get("constituencyName"));
        Candidate candidate = new Candidate(record.get("forename"), record.get("surname"), new Party(record.get("partyNameAbrv")));
        try {
            return new ElectionResult(constituency, candidate, NumberFormat.getIntegerInstance().parse(record.get("votes")).intValue(), Double.parseDouble(record.get("voteShare")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<CSVRecord> readCsv(String fileName) throws IOException {
        InputStream is = Main.class.getClassLoader().getResourceAsStream(fileName);
        CSVFormat csvFormat = CSVFormat.RFC4180.withHeader(HEADERS).withSkipHeaderRecord();
        CSVParser parser = new CSVParser(new BufferedReader(new InputStreamReader(is)), csvFormat);
        return parser.getRecords();
    }

    private static List<String> readFile(String fileName) throws IOException {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(fileName)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            return br.lines().collect(Collectors.toList());
        }
    }

    @Data
    @AllArgsConstructor
    public static class ElectionResults {
        private int year;
        private String description;
        private final Map<Constituency, List<ElectionResult>> results = new HashMap<>();
    }

    @Data
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode(of = {"constituency", "candidate"})
    public static class ElectionResult implements Comparable<ElectionResult> {
        private Constituency constituency;
        private Candidate candidate;
        private int votes;
        private double voteShare;

        @Override
        public int compareTo(ElectionResult that) {
            return this.constituency.id != that.constituency.id ? that.constituency.id - this.constituency.id : that.votes - this.votes;
        }
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(of = {"id"})
    public static class Constituency {
        private int id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Candidate {
        private String firstName;
        private String lastName;
        private Party party;
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Party {
        private String partyName;
    }
}

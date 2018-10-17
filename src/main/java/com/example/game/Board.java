package com.example.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

	private String name = "classic catan";
	private List<Field> fields;
	private Set<Edge> edges;
	private Set<Vertix> vertices;

	public String getName() {
		return this.name;
	}

	public List<Field> getFields() {
		return fields;
	}

	public Set<Edge> getEdges() {
		return this.edges;
	}

	public Set<Vertix> getVertices() {
		return this.vertices;
	}

	public Board() {
	}

	public static class BoardBuilder {

		public static Board classicBoard() {
			Board board = new Board();
			board.fields = new ArrayList<>();

			int[][] fieldsCoordinates = { { 2, 1 }, { 3, 1 }, { 4, 1 }, //
					{ 2, 2 }, { 3, 2 }, { 4, 2 }, { 5, 2 }, //
					{ 1, 3 }, { 2, 3 }, { 3, 3 }, { 4, 3 }, { 5, 3 }, //
					{ 2, 4 }, { 3, 4 }, { 4, 4 }, { 5, 4 }, //
					{ 2, 5 }, { 3, 5 }, { 4, 5 } };

//			 int[][] fieldsCoordinates = { { 2, 2 }, { 3, 2 }, //
//			 { 1, 3 }, { 2, 3 }, { 3, 3 }, //
//			 { 2, 4 }, { 3, 4 } };

			for (int[] coord : fieldsCoordinates)
				board.fields.add(new Field(coord[0], coord[1]));

			board.edges = generateEdges(fieldsCoordinates);
			board.vertices = generateVertices(fieldsCoordinates);

			return board;
		}

		private static Set<Vertix> generateVertices(int[][] fieldsCoordinates) {
			Set<Vertix> vertices = new HashSet<>();

			for (int[] is : fieldsCoordinates) {
				vertices.add(new Vertix(is[0], is[1], 1));
				vertices.add(new Vertix(is[0] + is[1] % 2, is[1] - 1, 2));
				vertices.add(new Vertix(is[0] + is[1] % 2, is[1] + 1, 1));
				vertices.add(new Vertix(is[0], is[1], 2));
				vertices.add(new Vertix(is[0] - 1 + is[1] % 2, is[1] + 1, 1));
				vertices.add(new Vertix(is[0] - 1 + is[1] % 2, is[1] - 1, 2));

			}
			return vertices;
		}

		private static Set<Edge> generateEdges(int[][] fieldsCoordinates) {
			Set<Edge> edges = new HashSet<>();

			for (int[] is : fieldsCoordinates) {
				edges.add(new Edge(is[0], is[1], 1));
				edges.add(new Edge(is[0], is[1], 2));
				edges.add(new Edge(is[0], is[1], 3));
				edges.add(new Edge(is[0] + is[1] % 2, is[1] + 1, 1));
				edges.add(new Edge(is[0] + 1, is[1], 2));
				edges.add(new Edge(is[0] + is[1] % 2, is[1] - 1, 3));
			}
			return edges;
		}
	}

}

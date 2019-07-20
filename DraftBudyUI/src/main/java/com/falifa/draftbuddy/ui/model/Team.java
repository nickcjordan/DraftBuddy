package com.falifa.draftbuddy.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.falifa.draftbuddy.ui.comparator.PlayerDraftedOrderComparator;
import com.falifa.draftbuddy.ui.constants.Position;

public class Team {

	String name;
	public List<Player> qb;
	public List<Player> rb;
	public List<Player> wr;
	public List<Player> te;
	public List<Player> k;
	public List<Player> d;
	int id;
	String city;
	List<Player> draftedPlayersInOrder;
	HashMap<Position, List<Player>> playersByPosition;
	String fullName;
	String abbrev;
	
	
	public Team(String name, String city, int id, String abbrev) {
		super();
		this.name = name;
		this.abbrev = abbrev;
		qb = new ArrayList<Player>();
		rb = new ArrayList<Player>();
		wr = new ArrayList<Player>();
		te = new ArrayList<Player>();
		k = new ArrayList<Player>();
		d = new ArrayList<Player>();
		this.id = id;
		this.city = city;
		playersByPosition = buildPlayersByPosition();
		this.fullName = city + " " + name;
	}


	public List<Player> getDraftedPlayersInOrder() {
		return draftedPlayersInOrder;
	}


	public void setDraftedPlayersInOrder(List<Player> draftedPlayersInOrder) {
		this.draftedPlayersInOrder = draftedPlayersInOrder;
	}


	public HashMap<Position, List<Player>> getPlayersByPosition() {
		return playersByPosition;
	}


	public void setPlayersByPosition(HashMap<Position, List<Player>> playersByPosition) {
		this.playersByPosition = playersByPosition;
	}


	public String getAbbrev() {
		return abbrev;
	}


	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public int getNumberOfPlayersOnTeam() {
		return qb.size() + rb.size() + wr.size() + te.size() + k.size() + d.size();
	}


	public List<Player> getAllInDraftedOrder() {
		return createDraftedPlayersInOrder();
	}
	
	public List<Player> getPlayersByPosition(Position position) {
		return playersByPosition.get(position);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addQb(Player player){
		qb.add(player);
	}
	
	public void addRb(Player player){
		rb.add(player);
	}
	
	public void addWr(Player player){
		wr.add(player);
	}
	
	public void addTe(Player player){
		te.add(player);
	}
	
	public void addK(Player player){
		k.add(player);
	}
	
	public void addD(Player player){
		d.add(player);
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Player> getQb() {
		return qb;
	}

	public void setQb(List<Player> qb) {
		this.qb = qb;
	}

	public List<Player> getRb() {
		return rb;
	}

	public void setRb(List<Player> rb) {
		this.rb = rb;
	}

	public List<Player> getWr() {
		return wr;
	}

	public void setWr(List<Player> wr) {
		this.wr = wr;
	}

	public List<Player> getTe() {
		return te;
	}

	public void setTe(List<Player> te) {
		this.te = te;
	}

	public List<Player> getK() {
		return k;
	}

	public void setK(List<Player> k) {
		this.k = k;
	}

	public List<Player> getD() {
		return d;
	}

	public void setD(List<Player> d) {
		this.d = d;
	}

	public String getFullName() {
		return fullName;
	}
	
	private List<Player> createDraftedPlayersInOrder() {
		List<Player> allPlayers = new ArrayList<Player>();
		allPlayers.addAll(qb);
		allPlayers.addAll(rb);
		allPlayers.addAll(wr);
		allPlayers.addAll(te);
		allPlayers.addAll(k);
		allPlayers.addAll(d);
		Collections.sort(allPlayers, new PlayerDraftedOrderComparator());
		return allPlayers;
	}

	private HashMap<Position, List<Player>> buildPlayersByPosition() {
		HashMap<Position, List<Player>> players = new HashMap<Position, List<Player>>();
		players.put(Position.QUARTERBACK, qb);
		players.put(Position.RUNNINGBACK, rb);
		players.put(Position.WIDERECEIVER, wr);
		players.put(Position.TIGHTEND, te);
		players.put(Position.KICKER, k);
		players.put(Position.DEFENSE, d);
		return players;
	}
	
	public List<List<Player>> buildPlayersByPositionList() {
		List<List<Player>> positions = new ArrayList<List<Player>>();
		positions.add(qb);
		positions.add(rb);
		positions.add(wr);
		positions.add(te);
		positions.add(k);
		positions.add(d);
		return positions;
	}

	public List<List<Player>> getPositionLists() {
		List<List<Player>> positions = new ArrayList<List<Player>>();
		positions.add(getQb());
		positions.add(getRb());
		positions.add(getWr());
		positions.add(getTe());
		positions.add(getK());
		positions.add(getD());
		return positions;
	}
	
	
}

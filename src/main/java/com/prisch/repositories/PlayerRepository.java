package com.prisch.repositories;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.prisch.content.NetballContentProvider;
import com.prisch.model.Player;
import com.prisch.model.Team;

import java.util.*;

public class PlayerRepository {

    private Context context;

    private TeamRepository teamRepository;

    public PlayerRepository(Context context) {
        this.context = context;

        this.teamRepository = new TeamRepository(context);
    }

    // ===== Interface =====

    public long createPlayer(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Player.NAME, name);

        Uri resultUri = context.getContentResolver().insert(NetballContentProvider.URI_PLAYERS, contentValues);
        return ContentUris.parseId(resultUri);
    }

    public void deletePlayer(Long id) {
        String where = Player.ID + "=?";
        String[] parameters = new String[] {id.toString()};

        context.getContentResolver().delete(NetballContentProvider.URI_PLAYERS, where, parameters);
    }

    public void renamePlayer(Long id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Player.NAME, name);

        String where = Player.ID + "=?";
        String[] parameters = new String[] {id.toString()};

        context.getContentResolver().update(NetballContentProvider.URI_PLAYERS, contentValues, where, parameters);
    }

    public Cursor getAllPlayers() {
        return context.getContentResolver().query(NetballContentProvider.URI_PLAYERS, null, null, null, Player.NAME);
    }

    public CursorLoader getAllPlayersLoader() {
        return new CursorLoader(context, NetballContentProvider.URI_PLAYERS, null, null, null, Player.NAME);
    }

    public Map<Boolean, List<Player>> getAllPlayersPartitionedByGame(Long gameId) {
        Set<Long> gamePlayerIds = new HashSet<Long>();
        List<Player> gamePlayers = new LinkedList<Player>();
        List<Player> otherPlayers = new LinkedList<Player>();

        Cursor teamCursor = teamRepository.getTeamForGame(gameId);
        while (teamCursor.moveToNext()) {
            Player player = new Player(teamCursor.getLong(teamCursor.getColumnIndex(Team.PLAYER_ID)), teamCursor.getString(teamCursor.getColumnIndex(Player.NAME)));
            gamePlayerIds.add(player.getId());
            gamePlayers.add(player);
        }

        Cursor playersCursor = getAllPlayers();
        while (playersCursor.moveToNext()) {
            Player player = new Player(playersCursor.getLong(playersCursor.getColumnIndex(Player.ID)), playersCursor.getString(playersCursor.getColumnIndex(Player.NAME)));
            if (!gamePlayerIds.contains(player.getId())) {
                otherPlayers.add(player);
            }
        }

        Map<Boolean, List<Player>> partitionedPlayersMap = new HashMap<Boolean, List<Player>>();
        partitionedPlayersMap.put(true, gamePlayers);
        partitionedPlayersMap.put(false, otherPlayers);

        return partitionedPlayersMap;
    }
}

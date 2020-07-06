# FantasyFootballUI
Application that helps me draft for fantasy football

## Notes:
To refresh data for new year...
- delete nfl.json and players.json of UI's src/main/resources/data/master
- call API to update API's local cache :: http://localhost:8081/api/update
- call UI to update UI's players.json and nfl.json

If an NFL team name changes...
- update team details in UI.constants.NflTeamMetadata enum

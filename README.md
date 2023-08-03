FantasyFootballUI
Application that helps me draft for fantasy football

## Notes:
To refresh data for new year...
- delete nfl.json and players.json of UI's src/main/resources/data/master
- call API to update API's local cache :: http://localhost:8081/api/update (can be done from UI start page)
- call UI to update UI's players.json and nfl.json (can be done from UI start page)

If an NFL team name changes...
- update team details in UI.constants.NflTeamMetadata enum

For fantasy footballers data...
- log in to the site and download each of the HTML pages, only way to do it since it needs authentication

In NflTeamMetadata:
- update "newCoach" field of enum
- update "oLineRank" field of enum

Update draft order:
- DraftBuddyAPI --> com.falifa.draftbuddy.api.model.DraftType 

Update Sleeper player JSON file:
- can only call this url once per day so BE CAREFUL
- https://api.sleeper.app/v1/players/nfl
- save to DraftBuddyUI --> src/main/resources/data/master/sleeper_players.json
- use postman to call :: the call isn't built into the code since we can only call once per day

Update JJ Zachariason - Late Round Draft Guide rankings:
- save excel file as csv to src/main/resources/data/LateRoundDraftGuideRankings.csv
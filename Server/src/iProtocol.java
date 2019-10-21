
public interface iProtocol {
	final int LOG = 0;
	final int WAITING = 1;
	final int ONLINE = 2;
	final int CHAT_ROOM_ADD_REMOVE = 1;
	final int CHAT_ROOM_CHANGE = 2;
	
	final int USER_UPDATE = 10001; 
	final int ID_BAN = 99999;
	final int ID_BAN_CANCEL = 11111;
	final int CONNECT = 22222;
	final int CONNECT_OK = 33333;
	final int CONNECT_DISABLE = 44444;
	final int SEND_MASTER_MESSAGE = 123456;
	
	final int CHANGE_SORT = 950;
	final int CHANGE_SORT_OK = 9501;
	
	
	
	
	final int LOGIN = 100;
	final int LOGIN_OK = 1001;
	final int LOGIN_IDMISMATCH = 1003;
	final int LOGIN_PWMISMATCH = 1004;
	final int LOGIN_IDBAN = 1005;
	final int LOGIN_ALREADY_LOGIN = 1006;//이미 접속되어있는겨웅
	
	final int JOIN = 110;
	final int JOIN_OK = 1101;
	final int JOIN_IDEXIST = 1102;
	final int JOIN_PWMISMATCH = 1103;
	final int JOIN_BLANKEXIST = 1104;
	
	final int LOGOUT = 120;
	final int LOGOUT_OK = 1201;

	
	final int WITHDRAW = 130;
	final int WITHDRAW_OK = 1301;
	final int WITHDRAW_FAIL = 1302;
	
	
	final int OPEN_CHAT_ROOM = 210;
	final int OPEN_CHAT_ROOM_OK = 2101;
	final int OPEN_CHAT_NOTICE = 2102;
	final int OPEN_CHAT_WITH_LOCK = 2103;
	final int OPEN_CHAT_FAIL = 2104;
	final int OPEN_CHAT_OVERUER = 2105;
	
	final int OPEN_LOCK_CHAT_ROOM = 220;
	final int RESPOND_OPEN_CHAT_WITH_LOCK_FAIL = 22041;
	
	
	
	final int SETUP_CHAT_ROOM = 230;
	final int SETUP_CHAT_ROOM__OK = 2301;
	final int CHAT_ROOM_LIST_UPDATE = 2302;
	
	final int SEARCH_CHATROOM = 240;
	final int SEARCH_CHATROOM_OK = 2401;
	final int SEARCH_CHATROOM_FAIL = 2402;
	
	final int CHATROOM_SET_CHANGE = 250;
	final int CHATROOM_SET_CHANGE_OK = 2501;
	final int CHATROOM_SET_CHANGE_FAIL =2502;
	
	final int CHATROOM_VOTE = 260;
	final int CHATROOM_VOTE_RESULTBACK = 261;
	final int CHATROOM_VOTE_SPREAD = 2601;
	final int CHATROOM_VOTE_RESULTBACK_SPREAD =2611;
	final int VOTE_END = 262;
	final int VOTE_END_OK = 2621;
	final int VOTE_RESULT_MESSAGE = 263;
	final int VOTE_RESULT_MESSAGE_OK =2631;
	
	final int CHATROOM_FAST_ENTER =270; //0703박상욱
	final int CHATROOM_FAST_ENTER_FAIL = 2701;
	
	final int BROADCAST_MSG = 300;
	final int BROADCAST_MSG_OK = 3001;
	
	final int ONLINE_USER_LIST = 310;
	final int ONLINE_USER_LIST_OK = 3101;
	
	final int WHISPER = 311;
	final int WHISPER_OK = 3111;
	final int WHISPER_OK_NOTICE = 3112;
	final int WHISPER_FAIL = 3113;
	
	final int WHISPER_IN_ROOM = 312;
	final int WHISPER_IN_ROOM_OK = 3121;
	final int CAPTAIN_TOSS = 410;
	final int CAPTAIN_TOSS_OK = 4101;
	final int CAPTAIN_TOSS_FAIL = 4102;
	
	final int USER_KICK = 420;
	final int USER_KICK_OK = 4201;
	final int USER_KICK_NOTICE = 4202;
	final int USER_KICK_FAIL = 4203;
	
	final int EXIT_CHATROOM = 430;
	final int EXIT_CHATROOM_OK = 4301;
	final int EXIT_CHATROOM_NOTICE = 4302;
	final int WATTING_USER_UPDATE = 4303;
	
	final int SET_NOTICE = 440;
	final int SET_NOTICE_OK = 4401;
	
	final int DEL_NOTICE = 441;
	final int DEL_NOTICE_OK = 4411;
	
	final int USER_INVITE_LIST = 460;
	final int USER_INVITE_LIST_OK = 4601;
	
	final int USER_INVITE = 461;
	final int RESPOND_USER_INVITE = 4611;
	final int USER_INVITE_RESPOND_OK = 462;
	final int USER_INVITE_ENTER = 4621;
	final int CHAT_ROOM_USER_UPDATE = 4622;
	
	final int USER_INVITE_RESPOND_NO = 463;
	final int USER_INVITE_FAIL = 4631;
	
	final int SEARCH_USER_LOCATION = 500;
	final int SEARCH_USER_LOCATION_OK = 5001;
	
	final int WAITING_ROOM_USER = 600;
	final int WAITING_ROOM_USER_OK = 6001;
	
	final int TOTAL_USER = 700;
	final int TOTAL_USER_OK = 7001;
	
	final int USER_PROFILE = 800;
	final int USER_PROFILE_OK = 8001;
	
	final int MODIFY_MY_PROFILE = 810;
	final int MODIFY_MY_PROFILE_OK = 8101;
	final int MY_PROFILE_OK = 8102;
	
	final int SEND_NOTE = 900;
	final int SEND_NOTE_OK = 9001;
	final int SEND_NOTE_NOTICE = 9002;
	final int SEND_NOTE_FAIL = 9003;
	
	final int OPEN_NOTEBOX = 910;
	final int OPEN_NOTELIST_OK = 9101;
	final int OPEN_NOTE = 920;
	final int OPEN_NOTE_OK = 9201;
	final int DELETE_NOTE = 930;
	final int DELETE_NOTE_OK = 9301;
	
	final int UPDATE_ROOMLIST = 940;
	final int UPDATE_ROOMLIST_OK = 9401;
	
	
	
	
	
	
//**********************************************************민지영****************************************************

	final int USER_FRIEND_ADD = 960;
	final int USER_FRIEND_ADD_OK = 9601;
	final int USER_FRIEND_ADD_NO = 9602;
	
	final int USER_FRIEND_REMOVE = 961;
	final int USER_FRIEND_REMOVE_OK = 9611;
	
	final int USER_BLAKC_ADD = 970;
	final int USER_BLAKC_ADD_OK = 9701;
	final int USER_BLAKC_ADD_NO = 9702;
	
	final int USER_BLAKC_REMOVE = 971;
	final int USER_BLAKC_REMOVE_OK = 9711;
//**********************************************************민지영****************************************************
	final int CHATROOM_SEND_NOTE = 980;
	final int CHATROOM_SEND_NOTE_OK = 9801;
			
	
	
	
			
			
}

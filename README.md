# EngineX

Enterprise grade scripting plugin for Minecraft servers.

## What's working?
- Basic commands
- Command aliases
- Command effects (text, raw_text)

## TODO
- More robust script loading from scripts/ directory instead of one test.conf file
- Command permissions checking
- Runtime load/unload of scripts using /enginex command

### Example test.conf
```
plugin {
    name "FunnyGuilds";
    version "1.0-SNAPSHOT";
    author "Daffit";
}

command "g" "guild" "gildia" {

    permission "funnyguilds.command";
    description "FunnyGuilds main command.";

    action {
        do {
            text "&ePomoc pluginu FunnyGuilds:";
            text "&f/g &7- komendy gildii";
            text "&f/g plugin &7- informacje o wtyczce";
        }
    }

    action "admin" {
        permission "funnyguilds.admin";
        do {
            text "&cWow ju admin?";
        }
    }
}

command "test" {

    action {
        do {
            text "&c&lHello World!";
            raw_text "&c&lHello World!";
        }
    }
}

command "test2" {

    action {
        do indexed {
            100 text "Hello $sender#getName!";
            200 set _ 0;
            300 text "$_";
            400 increase _ 1;
            401 increase _ 0.5;
            500 text "woah! $_";
            401 increase _ -0.5;
            501 stop;
            600 increase _ 0.5;
            #600 when_less _ 10 -> goto 300;
        }
    }
}
```
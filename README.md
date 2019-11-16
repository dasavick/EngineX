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

    action "plugin" {
        do {
            text "&e$plugin.name $plugin.version by $plugin.author";
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
```
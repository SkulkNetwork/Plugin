// The main plugin used for the Skulk Network Minecraft server.
// Copyright (C) 2022-present  RGBCube
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

package network.skulk.plugin.core;

import network.skulk.plugin.core.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.plugin.core.extensions.foodbehaviour.FoodBehaviourExtension;
import network.skulk.plugin.core.extensions.homes.HomesExtension;
import network.skulk.plugin.core.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.plugin.core.extensions.silencemobs.SilenceMobsExtension;
import network.skulk.plugin.core.extensions.tpa.TPAExtension;
import network.skulk.plugin.wrapper.BasePlugin;

// TODO: Make custom maps and solve "for \(final \w+ \w+ : [\w. \(\)]+ \{\n[ ]+if"
public final class Plugin extends BasePlugin {
    @Override protected void initExtensions() {
        new EntityOverrideExtension(this);
        new FoodBehaviourExtension(this);
        new HomesExtension(this);
        new MessageOverrideExtension(this);
        new SilenceMobsExtension(this);
        new TPAExtension(this);
    }
}

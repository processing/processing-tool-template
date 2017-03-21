/**
 * Manages Derby server.
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 *
 * @author   ##author##
 * @modified ##date##
 * @version  ##tool.prettyVersion##
 */

package dbmanager;

import processing.app.Base;
import processing.app.tools.Tool;

// when creating a tool, the name of the main class which implements Tool must
// be the same as the value defined for project.name in your build.properties

public class DBManager implements Tool {
	Base base;
	DBGUIFrame frame;

	public String getMenuTitle() {
		return "##tool.name##";
	}

	public void init(Base base) {

		// Store a reference to the Processing application itself
		this.base = base;

	}

	public void run() {
		
		if (frame == null) {
			System.out.println("Frame creation");
			try {
				frame = new DBGUIFrame();
				frame.setLocationRelativeTo(null);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error in Frame creation");
			}

		}

		frame.setVisible(true);

	}

	// Get the currently active Editor to run the Tool on it
	// Editor editor = base.getActiveEditor();
	// editor.setText("Deleted your code. What now?");

	// Fill in author.name, author.url, tool.prettyVersion and
	// project.prettyName in build.properties for them to be auto-replaced here.
	// System.out.println("Hello Tool. ##tool.name## ##tool.prettyVersion## by
	// ##author##");
}

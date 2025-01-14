package ie.tudublin;

import javax.swing.ToolTipManager;

import processing.core.PApplet;



public class Arrays extends PApplet
{
	String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

	float[] rainfall = {200, 260, 300, 150, 100, 50, 10, 40, 67, 160, 400, 420};

	int mode = 0;

	public float map1(float a, float b, float c, float d, float e)
	{
		float r1 = c - b;
		float r2 = e - d;

		float howFar = a - b;
		return d + (howFar / r1) * r2;
	}

	void randomize()
	{
		for (int i = 0; i < rainfall.length; i++)
		{
			rainfall[i] = random(450);
		}
	}

	public void settings()
	{
		size(500, 500);

		String[] m1 = months;
		// months[0] = "XXX"; ?

		print(m1[0]);

		for(int i = 0; i < months.length; i ++)
		{
			println("Month: " + months[i] + "\t" + rainfall[i]);
		}

		for (String s : m1)
		{
			println(s);
		}

		int minIndex = 0;
		for(int i = 0 ; i < rainfall.length ; i ++)
		{
			if (rainfall[i] < rainfall[minIndex])
			{
				minIndex = i;
			}
		}
		
		int maxIndex = 0;
		for(int i = 0 ; i < rainfall.length ; i ++)
		{
			if (rainfall[i] > rainfall[maxIndex])
			{
				maxIndex = i;
			}
		}

		println("The month with the minimum rainfall was " + months[minIndex] + " with " + rainfall[minIndex] + "rain");
		println("The month with the max rainfall was " + months[maxIndex] + " with " + rainfall[maxIndex] + "rain");
		
		
		float tot = 0;
		for(float f:rainfall)
		{
			tot += f;
		}

		float avg = tot / (float) rainfall.length;

		// a b-c d-e;
		println(map1(5, 0, 10, 0, 100));
		// 50

		println(map1(25, 20, 30, 200, 300));
		// 250

		println(map1(26, 25, 35, 0, 100));
		// 10 


	}

	public void setup()
	{
		colorMode(HSB);
		background(0);
		randomize();
	}

	public void keyPressed()
	{
		if (key >= '0' && key <= '9')
		{
			mode = key - '0';
		}

		println(mode);
	}
	
	public void draw()
	{	
		background(0); // Black background

		// Switch statement to control which chart is displayed
		switch(mode)
		{
			// BAR CHART
			case 1 :

				// Axis
				fill(0,100,0);
				stroke(255);
				rect(50, 450, 1000, 3);
				rect(50, 0, 3, 450);

				// Bars
				float w = width / (float)months.length;

				for(int i = 0 ; i < months.length ;  i++)
				{
					stroke(0);
					fill(i * 25, 100, 100);
					float x = map1(i, 0, months.length, 54, width);
					float y = map1(i, 0, months.length, height - 50, 0);
					rect(x, 449, w, -rainfall[i]);
					
					// Text
					text(months[i], x, 480);
					text((40 * i), 20, y);
				}

				fill(255);
				text("Bar Chart", 260, 30);

				break;

			// TREND LINE
			case 2 :
		
				// Axis
				fill(0,100,0);
				stroke(255);
				rect(50, 450, 1000, 3);
				rect(50, 0, 3, 450);

				for(int i = 0 ; i < months.length - 1 ;  i++)
				{
					stroke(255);
					float x = map1(i, 0, months.length, 54, width);
					float x1 = map1(i + 1, 0, months.length, 54, width);
					float y = map1(i, 0, months.length, height - 50, 0);

					if(i == 0)
					{
						line(50, 450, x1, rainfall[i + 1]);
					}

					else
					{
						line(x, rainfall[i], x1, rainfall[i + 1]);
					}
					
					// Text
					fill(i * 25, 100, 100);
					text(months[i], x, 480);
					text((40 * i), 20, y);
				}

				fill(255);
				text("Line Graph", 260, 30);

				break;

				// PIE CHART
				case 3 :

					float diameter = 300;
					float lastAngle = 0;

					// Total rainfall
					float tot = 0;
					for(float f:rainfall)
					{
						tot += f;
					}

					for (int i = 0; i < rainfall.length; i++)
					{
						float hue = map(i, 0, rainfall.length, 0, 255);
						fill(hue, 255, 255);

						float angle = map(rainfall[i], 0, tot, 0, TWO_PI);
						arc(width / 2, height / 2, diameter, diameter, lastAngle, lastAngle + angle, PIE);
						
						float labelAngle = lastAngle + angle / 2;
						float textX = width / 2 + (diameter / 2 + 20) * cos(labelAngle);
						float textY = height / 2 + (diameter / 2 + 20) * sin(labelAngle);
						
						lastAngle += angle;

						// Text
						textAlign(CENTER, CENTER);
						text(months[i], textX, textY);
					}
					
					// Title
					fill(255);
					text("Rainfall piechart", 260, 30);

					break;
			}
		}
	}	

BEGIN	{newlines=0}
NF == 1	{newlines=newlines+1;} 
NF != 1 {
				printf("\\n"); 
	 
				if (newlines>1) printf("\\n"); 
				newlines=0; 
	
				printf("\"+\n\""); 
	
				for (i=1; i<=NF; ++i) {
					l = length($i);
					
					for (j=1; j<=l; ++j) {
						c = substr($i, j, 1);
						if (c == "\"" || c == "“" || c =="”") printf ("\\\"");
						else if (c == "-") printf("-");
						else if (c != "\n" && c != "\r") printf ("%c", c);
					}
					
					printf (" ");
					
				}
				
			}
			
END	{printf("\\n\"");}


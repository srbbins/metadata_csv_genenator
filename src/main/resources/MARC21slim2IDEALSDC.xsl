<?xml version="1.0" encoding="UTF-8"?>
<!-- 
	This stylesheet creates DSpace formatted qualified Dublin Core for the IDEALS 
	instiutional repository at the University of Illinois at Urbana-Champaign. 
	
	Stylesheet based on the MARC -> OAI DC transformation:
	
	http://www.loc.gov/standards/marcxml/xslt/MARC21slim2OAIDC.xsl 
	
	modified for IDEALS by Matt Cordial - 10/30/2007 
-->
<xsl:stylesheet version="1.0" xmlns:marc="http://www.loc.gov/MARC21/slim" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" exclude-result-prefixes="marc">
	<xsl:import href="MARC21slimUtils.xsl"/>
	<xsl:output method="xml" indent="yes"/>

	<xsl:template match="marc:record">
		
		<xsl:variable name="leader" select="marc:leader"/>
		<xsl:variable name="leader6" select="substring($leader,7,1)"/>
		<xsl:variable name="leader7" select="substring($leader,8,1)"/>
		<xsl:variable name="controlField008" select="marc:controlfield[@tag=008]"/>
		<dublin_core>
			
			<!-- title -->
			<xsl:for-each select="marc:datafield[@tag=245]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="title" qualifier="none">
					<xsl:variable name="ti">
						<xsl:call-template name="subfieldSelect">
							<xsl:with-param name="codes">abfghkn</xsl:with-param>
						</xsl:call-template>
					</xsl:variable>
					<xsl:call-template name="stripTrailingSlash">
						<xsl:with-param name="title" select="normalize-space($ti)" />
					</xsl:call-template>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- title:alternative -->
			<xsl:for-each select="marc:datafield[@tag=130]|marc:datafield[@tag=210]|marc:datafield[@tag=240]|marc:datafield[@tag=246]|marc:datafield[@tag=730]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="title" qualifier="alternative">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- creator -->
			<xsl:for-each select="marc:datafield[@tag=100]|marc:datafield[@tag=110]|marc:datafield[@tag=111]|marc:datafield[@tag=700]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="creator" qualifier="none">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- subject -->
			<xsl:for-each select="marc:datafield[@tag=600]|marc:datafield[@tag=610]|marc:datafield[@tag=611]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="subject" qualifier="none">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="marc:datafield[@tag=630]/marc:subfield[@code='a']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="subject" qualifier="none">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>	
			</xsl:for-each>
			<xsl:for-each select="marc:datafield[@tag=650]">
				<xsl:variable name="str">
					<!--check to see if there are values in the sub fields, if so we need to dash seperate them-->
					<xsl:if test="marc:subfield[@code='a']">
						<xsl:value-of select="normalize-space(marc:subfield[@code='a'])"/>
					</xsl:if>
					<xsl:if test="marc:subfield[@code='b']">
						-- <xsl:value-of select="normalize-space(marc:subfield[@code='b'])"/>
					</xsl:if>
					<xsl:if test="marc:subfield[@code='x']">
						-- <xsl:value-of select="normalize-space(marc:subfield[@code='x'])"/>
					</xsl:if>
				</xsl:variable>
				<xsl:if test="normalize-space(.)">
				<dcvalue element="subject" qualifier="none">
					<xsl:value-of select="normalize-space($str)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="marc:datafield[@tag=653]">
				<xsl:variable name="str">
					<!--check to see if there are values in the sub fields, if so we need to dash seperate them-->
					<xsl:if test="marc:subfield[@code='a']">
						<xsl:value-of select="normalize-space(marc:subfield[@code='a'])"/>
					</xsl:if>
					<xsl:if test="marc:subfield[@code='b']">
						-- <xsl:value-of select="normalize-space(marc:subfield[@code='b'])"/>
					</xsl:if>
					<xsl:if test="marc:subfield[@code='c']">
						<xsl:value-of select="normalize-space(marc:subfield[@code='c'])"/>
					</xsl:if>
					<xsl:if test="marc:subfield[@code='d']">
						-- <xsl:value-of select="normalize-space(marc:subfield[@code='d'])"/>
					</xsl:if>
					<xsl:if test="marc:subfield[@code='q']">
						-- <xsl:value-of select="normalize-space(marc:subfield[@code='q'])"/>
					</xsl:if>
				</xsl:variable>
				<xsl:if test="normalize-space(.)">
				<dcvalue element="subject" qualifier="none">
					<xsl:value-of select="normalize-space($str)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- description -->
			<xsl:for-each select="marc:datafield[500&lt;= @tag and @tag&lt;= 599 ][not(@tag=505 or @tag=506 or @tag=520 or @tag=530 or @tag=540 or @tag=546)]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="description" qualifier="none">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- description:tableofcontents -->
			<xsl:for-each select="marc:datafield[@tag=505]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="description" qualifier="tableofcontents">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- description:abstract -->
			<xsl:for-each select="marc:datafield[@tag=520]">
				<xsl:choose>
					<xsl:when test="@ind1=3">
						<dcvalue element="description" qualifier="abstract">
							<xsl:value-of select="normalize-space(.)"/>
						</dcvalue>
					</xsl:when>
					<xsl:otherwise>
						<xsl:if test="normalize-space(.)">
						<dcvalue element="description" qualifier="none">
							<xsl:value-of select="normalize-space(.)"/>
						</dcvalue>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
			
			<!-- contributor -->
			<xsl:for-each select="marc:datafield[@tag=710]|marc:datafield[@tag=711]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="contributor" qualifier="none">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- publisher -->
			<xsl:for-each select="marc:datafield[@tag=260]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="publisher" qualifier="none">
					<xsl:call-template name="subfieldSelect">
						<xsl:with-param name="codes">ab</xsl:with-param>
					</xsl:call-template>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- date:issued -->
			<xsl:for-each select="marc:datafield[@tag=260]/marc:subfield[@code='c']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="date" qualifier="issued">
					<xsl:value-of select="translate(normalize-space(.), '.,[]()-/cC?', '')"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- type -->
			<!-- Value will always be text for OCA content -->
			<dcvalue element="type" qualifier="none">text</dcvalue>
			
			<!-- type:genre -->
			<xsl:for-each select="marc:datafield[@tag=655]/marc:subfield[@code='a']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="type" qualifier="genre">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<xsl:for-each select="marc:datafield[@tag=650]/marc:subfield[@code='v']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="type" qualifier="genre">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- identifier:oclc -->
			<xsl:for-each select="marc:datafield[@tag=035]/marc:subfield[@code='a']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="identifier" qualifier="oclc">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- identifier:localBib -->
			<xsl:for-each select="marc:controlfield[@tag=001]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="identifier" qualifier="localBib">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- identifier:isbn -->
			<xsl:for-each select="marc:datafield[@tag=020]/marc:subfield[@code='a']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="identifier" qualifier="isbn">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- identifier:issn -->
			<xsl:for-each select="marc:datafield[@tag=022]/marc:subfield[@code='a']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="identifier" qualifier="issn">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
                        
                        <!-- identifier:uri -->
			<xsl:for-each select="marc:datafield[@tag=852]/marc:subfield[@code='u']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="identifier" qualifier="uri">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- language -->
			<xsl:if test="normalize-space(substring($controlField008,36,3))">
			<dcvalue element="language" qualifier="none">
				<xsl:value-of select="substring($controlField008,36,3)"/>
			</dcvalue>
			</xsl:if>
			
			<!-- relation:hasversion -->
			<xsl:for-each select="marc:datafield[@tag=856]/marc:subfield[@code='u']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="relation" qualifier="hasversion">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- relation:isreplacedby -->
			<xsl:for-each select="marc:datafield[@tag=785]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="relation" qualifier="isreplacedby">
					<xsl:call-template name="subfieldSelect">
						<xsl:with-param name="codes">adtwx</xsl:with-param>
					</xsl:call-template>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- relation:replaces -->
			<xsl:for-each select="marc:datafield[@tag=780]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="relation" qualifier="replaces">
					<xsl:call-template name="subfieldSelect">
						<xsl:with-param name="codes">adtwx</xsl:with-param>
					</xsl:call-template>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- relation:ispartof -->
			<!-- use 440 if available, if not available use 490, if 490 not available use 830  -->
			<xsl:choose>
				<xsl:when test="marc:datafield[@tag=440]">
					<xsl:for-each select="marc:datafield[@tag=440]">
						<xsl:if test="normalize-space(.)">
						<dcvalue element="relation" qualifier="ispartof">
							<xsl:value-of select="normalize-space(.)"/>
						</dcvalue>
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
				<xsl:when test="marc:datafield[@tag=490]">
					<xsl:for-each select="marc:datafield[@tag=490]">
						<xsl:if test="normalize-space(.)">
						<dcvalue element="relation" qualifier="ispartof">
							<xsl:value-of select="normalize-space(.)"/>
						</dcvalue>
						</xsl:if>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:for-each select="marc:datafield[@tag=830]">
						<xsl:if test="normalize-space(.)">
						<dcvalue element="relation" qualifier="ispartof">
							<xsl:value-of select="normalize-space(.)"/>
						</dcvalue>
						</xsl:if>
					</xsl:for-each>
				</xsl:otherwise>
			</xsl:choose>
			
			<!-- relation:isreferencedby -->
			<xsl:for-each select="marc:datafield[@tag=510]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="relation" qualifier="isreferencedby">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- coverage:spatial -->
			<xsl:for-each select="marc:datafield[@tag=752]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="coverage" qualifier="spatial">
					<xsl:call-template name="subfieldSelect">
						<xsl:with-param name="codes">abcdf</xsl:with-param>
					</xsl:call-template>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="marc:datafield[@tag=650]/marc:subfield[@code='z']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="coverage" qualifier="spatial">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="marc:datafield[@tag=651]/marc:subfield[@code='a']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="coverage" qualifier="spatial">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- coverage:temporal -->
			<xsl:for-each select="marc:datafield[@tag=650]/marc:subfield[@code='y']|marc:datafield[@tag=651]/marc:subfield[@code='y']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="coverage" qualifier="temporal">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- rights -->
			<xsl:for-each select="marc:datafield[@tag=506]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="rights" qualifier="none">
					<xsl:value-of select="marc:subfield[@code='a']"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="marc:datafield[@tag=540]">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="rights" qualifier="none">
					<xsl:value-of select="marc:subfield[@code='a']"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
			
			<!-- rights -->
			<!-- boilerplate with the date:issued value embedded -->
			<dcvalue element="rights" qualifier="none">Copyright <xsl:value-of select="translate(normalize-space(marc:datafield[@tag=260]/marc:subfield[@code='c']), '.,[]()-/cC?', '')"/> Board of Trustees University of Illinois.</dcvalue>
			
			<!-- format -->
			<xsl:for-each select="marc:datafield[@tag=856]/marc:subfield[@code='q']">
				<xsl:if test="normalize-space(.)">
				<dcvalue element="format" qualifier="none">
					<xsl:value-of select="normalize-space(.)"/>
				</dcvalue>
				</xsl:if>
			</xsl:for-each>
		</dublin_core>
	</xsl:template>
	
	<xsl:template name="stripTrailingSlash">
		<xsl:param name="title" />
		<xsl:choose>
			<xsl:when test="substring($title, string-length($title)) = '/'">
				<xsl:value-of select="substring($title,0,string-length($title)-1)"/>		
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$title"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="datafield">
		<xsl:param name="tag"/>
		<xsl:param name="ind1">
			<xsl:text> </xsl:text>
		</xsl:param>
		<xsl:param name="ind2">
			<xsl:text> </xsl:text>
		</xsl:param>
		<xsl:param name="subfields"/>
		<xsl:element name="marc:datafield">
			<xsl:attribute name="tag">
				<xsl:value-of select="$tag"/>
			</xsl:attribute>
			<xsl:attribute name="ind1">
				<xsl:value-of select="$ind1"/>
			</xsl:attribute>
			<xsl:attribute name="ind2">
				<xsl:value-of select="$ind2"/>
			</xsl:attribute>
			<xsl:copy-of select="$subfields"/>
		</xsl:element>
	</xsl:template>

	<xsl:template name="subfieldSelect">
		<xsl:param name="codes">abcdefghijklmnopqrstuvwxyz</xsl:param>
		<xsl:param name="delimeter">
			<xsl:text> </xsl:text>
		</xsl:param>
		<xsl:variable name="str">
			<xsl:for-each select="marc:subfield">
				<xsl:if test="contains($codes, @code)">
					<xsl:value-of select="text()"/>
					<xsl:value-of select="$delimeter"/>
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		<xsl:value-of select="substring($str,1,string-length($str)-string-length($delimeter))"/>
	</xsl:template>

	<xsl:template name="buildSpaces">
		<xsl:param name="spaces"/>
		<xsl:param name="char">
			<xsl:text> </xsl:text>
		</xsl:param>
		<xsl:if test="$spaces>0">
			<xsl:value-of select="$char"/>
			<xsl:call-template name="buildSpaces">
				<xsl:with-param name="spaces" select="$spaces - 1"/>
				<xsl:with-param name="char" select="$char"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<xsl:template name="chopPunctuation">
		<xsl:param name="chopString"/>
		<xsl:param name="punctuation">
			<xsl:text>.:,;/ </xsl:text>
		</xsl:param>
		<xsl:variable name="length" select="string-length($chopString)"/>
		<xsl:choose>
			<xsl:when test="$length=0"/>
			<xsl:when test="contains($punctuation, substring($chopString,$length,1))">
				<xsl:call-template name="chopPunctuation">
					<xsl:with-param name="chopString" select="substring($chopString,1,$length - 1)"/>
					<xsl:with-param name="punctuation" select="$punctuation"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="not($chopString)"/>
			<xsl:otherwise>
				<xsl:value-of select="$chopString"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="chopPunctuationFront">
		<xsl:param name="chopString"/>
		<xsl:variable name="length" select="string-length($chopString)"/>
		<xsl:choose>
			<xsl:when test="$length=0"/>
			<xsl:when test="contains('.:,;/[ ', substring($chopString,1,1))">
				<xsl:call-template name="chopPunctuationFront">
					<xsl:with-param name="chopString" select="substring($chopString,2,$length - 1)"
					/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="not($chopString)"/>
			<xsl:otherwise>
				<xsl:value-of select="$chopString"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="chopPunctuationBack">
		<xsl:param name="chopString"/>
		<xsl:param name="punctuation">
			<xsl:text>.:,;/] </xsl:text>
		</xsl:param>
		<xsl:variable name="length" select="string-length($chopString)"/>
		<xsl:choose>
			<xsl:when test="$length=0"/>
			<xsl:when test="contains($punctuation, substring($chopString,$length,1))">
				<xsl:call-template name="chopPunctuation">
					<xsl:with-param name="chopString" select="substring($chopString,1,$length - 1)"/>
					<xsl:with-param name="punctuation" select="$punctuation"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="not($chopString)"/>
			<xsl:otherwise>
				<xsl:value-of select="$chopString"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- nate added 12/14/2007 for lccn.loc.gov: url encode ampersand, etc. -->
	<xsl:template name="url-encode">

		<xsl:param name="str"/>

		<xsl:if test="$str">
			<xsl:variable name="first-char" select="substring($str,1,1)"/>
			<xsl:choose>
				<xsl:when test="contains($safe,$first-char)">
					<xsl:value-of select="$first-char"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="codepoint">
						<xsl:choose>
							<xsl:when test="contains($ascii,$first-char)">
								<xsl:value-of
										select="string-length(substring-before($ascii,$first-char)) + 32"
								/>
							</xsl:when>
							<xsl:when test="contains($latin1,$first-char)">
								<xsl:value-of
										select="string-length(substring-before($latin1,$first-char)) + 160"/>
								<!-- was 160 -->
							</xsl:when>
							<xsl:otherwise>
								<xsl:message terminate="no">Warning: string contains a character
									that is out of range! Substituting "?".</xsl:message>
								<xsl:text>63</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:variable name="hex-digit1"
								  select="substring($hex,floor($codepoint div 16) + 1,1)"/>
					<xsl:variable name="hex-digit2" select="substring($hex,$codepoint mod 16 + 1,1)"/>
					<!-- <xsl:value-of select="concat('%',$hex-digit2)"/> -->
					<xsl:value-of select="concat('%',$hex-digit1,$hex-digit2)"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="string-length($str) &gt; 1">
				<xsl:call-template name="url-encode">
					<xsl:with-param name="str" select="substring($str,2)"/>
				</xsl:call-template>
			</xsl:if>
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>
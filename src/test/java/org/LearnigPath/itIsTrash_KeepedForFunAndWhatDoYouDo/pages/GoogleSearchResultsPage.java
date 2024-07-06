package org.LearnigPath.itIsTrash_KeepedForFunAndWhatDoYouDo.pages;

import com.github.jsdevel.testng.selenium.AbstractPage;
import java.net.URL;

public class GoogleSearchResultsPage extends AbstractPage<GoogleSearchResultsPage, SamplePageFactory> {
  @Override
  public boolean isPageViewableFrom(URL proposedUrl) {
    return "/search".equals(proposedUrl.getPath());
  }
}
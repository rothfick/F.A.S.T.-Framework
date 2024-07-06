package org.LearnigPath.itIsTrash_KeepedForFunAndWhatDoYouDo.suites;

import com.github.jsdevel.testng.selenium.AbstractSuite;
import com.github.jsdevel.testng.selenium.annotations.driverconfig.UserAgent;
import com.github.jsdevel.testng.selenium.annotations.screensizes.LargeDesktop;
import com.github.jsdevel.testng.selenium.annotations.screensizes.Phone;
import com.github.jsdevel.testng.selenium.config.Config;
import com.github.jsdevel.testng.selenium.exceptions.PageInitializationException;
import com.github.jsdevel.testng.selenium.exceptions.PageInstantiationException;
import org.LearnigPath.itIsTrash_KeepedForFunAndWhatDoYouDo.pages.GoogleSearchResultsPage;
import org.LearnigPath.itIsTrash_KeepedForFunAndWhatDoYouDo.pages.SamplePageFactory;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import org.testng.annotations.Test;

public class SampleSuiteITest extends AbstractSuite<SamplePageFactory> {
  @Test
  public void we_should_be_able_to_view_google_with_phantom() {
    getPageFactory().getHomePage();
  }

  @Test
  public void we_should_be_able_to_pass_desired_urls_to_pages() {
    getPageFactory()
      .getHomePage("/intl/en/policies/privacy/?fg=1")
    ;
  }

  @Test
  public void we_should_be_able_to_return_new_pages_from_pages() {
    GoogleSearchResultsPage searchPage = getPageFactory()
      .getHomePage()
      .submitSearch("dinosaurs")
    ;
  }

  @Test(expectedExceptions = PageInstantiationException.class)
  public void we_should_get_an_error_when_calling_factory_methods_with_a_return_type_that_does_not_extend_Page() {
    getPageFactory().getFoo();
  }

  @Test(expectedExceptions = PageInitializationException.class, expectedExceptionsMessageRegExp = ".+can not be viewed from.+")
  public void we_should_get_an_error_when_initializing_a_page_that_is_not_viewable_from_the_current_url() {
    getPageFactory().getSearchResultsPage("/asdfasdf");
  }

  @Test @LargeDesktop
  public void we_should_be_able_to_control_screensize_via_annotations() {
    LargeDesktop screensize = (LargeDesktop) getPageFactory().getHomePage()
        .getContext().getScreensize();
    assertNotNull(screensize);
  }

  @Test
  public void we_should_be_default_screensize_to_Phone() {
    Phone screensize = (Phone) getPageFactory().getHomePage()
        .getContext().getScreensize();
    assertNotNull(screensize);
  }

  @Test @UserAgent("foo")
  public void we_should_be_able_to_set_the_user_agent_on_a_test_by_test_basis() {
    getPageFactory();
  }

  @Test
  public void we_should_be_able_to_configure_values_from_properties_files() {
    assertEquals("MyArtifact", Config.LOGGING_PREFIX);
  }
}
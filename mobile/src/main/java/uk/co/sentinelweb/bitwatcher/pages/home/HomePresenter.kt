package uk.co.sentinelweb.bitwatcher.pages.home

class HomePresenter(val homeView: HomeView) : HomeContract.Presenter{
    override fun view(): HomeContract.View {// todo dont do this
        return homeView
    }

    override fun loadData() {
        homeView.setData(HomeModel("MyPrice"));
    }

}
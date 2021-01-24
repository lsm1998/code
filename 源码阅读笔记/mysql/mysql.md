# mysql

### 网络模型

````
使用poll或者select
````

+ 代码片段：
````
Channel_info *Mysqld_socket_listener::listen_for_connection_event() {
// 是否支持poll
#ifdef HAVE_POLL
  int retval = poll(&m_poll_info.m_fds[0], m_socket_map.size(), -1);
#else
// 否则使用select
  m_select_info.m_read_fds = m_select_info.m_client_fds;
  int retval = select((int)m_select_info.m_max_used_connection,
                      &m_select_info.m_read_fds, 0, 0, 0);
#endif
后面代码略。。。
````

